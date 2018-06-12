package com.namics.oss.maven.summary;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Simple mojo that gathers all generated test report files and renders a summary to console at end of project.
 *
 * @author André Schäfer, Namics AG andre.schaefer@namics.com
 */
@Mojo(name = "summary", inheritByDefault = false)
public class SummaryMojo extends AbstractMojo {

	/**
	 * The Maven Project.
	 */
	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	protected MavenProject project;
	
	@Parameter(defaultValue = "true")
	protected boolean failOnErrors;
	
	/**
	 * The projects in the reactor for aggregation report.
	 */
	@Parameter(defaultValue = "${reactorProjects}", readonly = true)
	protected List<MavenProject> reactorProjects;

	protected List<File> resolvedReportsDirectories;

	/**
	 * Whether the report should be generated or not.
	 *
	 * @return {@code true} if and only if the report should be generated.
	 * @since 2.11
	 */
	protected boolean isSkipped() {
		return false;
	}

	@Override
	public void execute() throws MojoExecutionException {
		if (!hasReportDirectories()) {
			return;
		}
		PluginConsoleLogger logger = new PluginConsoleLogger(getLog());
		Report report = new SurefireReportAnalyser().analyse(getReportsDirectories(), logger);

		logger.info("------------------------------------------------------------------------");
		logger.info("|  Test Report                                                         |");
		logger.info("------------------------------------------------------------------------");
		report.getFailedTests().forEach(t -> logger.warning(t.toString()));
		logger.info("------------------------------------------------------------------------");
		logger.info("");
		logger.info(format(" \ttotal   : %10s", report.getTotalTests()));
		if (report.getTotalErrors() > 0) {
			logger.error(format("\terrors  : %10s", report.getTotalErrors()));
		} else {
			logger.info(format(" \terrors  : %10s", report.getTotalErrors()));
		}
		if (report.getTotalFailures() > 0) {
			logger.error(format("\tfailed  : %10s", report.getTotalFailures()));
		} else {
			logger.info(format(" \tfailed  : %10s", report.getTotalFailures()));
		}
		logger.info(format(" \tskipped : %10s", report.getTotalSkipped()));
		logger.info(format(" \tsuccess : %10.2f%% in %4.2f s", report.getTotalPercentage(), report.getTotalElapsedTime()));
		logger.info("------------------------------------------------------------------------");

		if ( failOnErrors){
			throw new MojoExecutionException(this,"There where " + report.getTotalErrors() + " errors and " + report.getTotalFailures() + " failed tests"  , report.toString());
		}
		//		private List<ReportTestCase> testDetails;

	}

	private boolean hasReportDirectories() {
		if (isSkipped()) {
			return false;
		}

		final List<File> reportsDirectories = getReportsDirectories();
		return reportsDirectories != null;
	}

	private List<File> getReportsDirectories() {
		if (resolvedReportsDirectories != null) {
			return resolvedReportsDirectories;
		}

		resolvedReportsDirectories = new ArrayList<>();

		if (!project.isExecutionRoot()) {
			return null;
		}
		for (MavenProject mavenProject : getProjectsWithoutRoot()) {
			resolvedReportsDirectories.add(getSurefireReportsDirectory(mavenProject));
			resolvedReportsDirectories.add(getFailsafeReportsDirectory(mavenProject));
		}
		if (resolvedReportsDirectories.isEmpty()) {
			resolvedReportsDirectories.add(getSurefireReportsDirectory(project));
			resolvedReportsDirectories.add(getFailsafeReportsDirectory(project));
		}
		return resolvedReportsDirectories;
	}

	private File getFailsafeReportsDirectory(MavenProject subProject) {
		String buildDir = subProject.getBuild().getDirectory();
		return new File(buildDir + "/surefire-reports");
	}

	private File getSurefireReportsDirectory(MavenProject subProject) {
		String buildDir = subProject.getBuild().getDirectory();
		return new File(buildDir + "/failsafe-reports");
	}

	private List<MavenProject> getProjectsWithoutRoot() {
		List<MavenProject> result = new ArrayList<>();
		for (MavenProject subProject : reactorProjects) {
			if (!project.equals(subProject)) {
				result.add(subProject);
			}
		}
		return result;
	}

}
