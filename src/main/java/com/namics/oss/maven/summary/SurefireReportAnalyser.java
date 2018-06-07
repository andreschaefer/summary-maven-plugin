/*
 * Copyright 2000-2018 Namics AG. All rights reserved.
 */

package com.namics.oss.maven.summary;

import org.apache.maven.plugin.surefire.log.api.ConsoleLogger;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;
import org.apache.maven.reporting.MavenReportException;

import java.io.File;
import java.util.List;
import java.util.Locale;

import static com.namics.oss.maven.summary.MathUtils.round;
import static java.util.stream.Collectors.toList;

/**
 * ReportAnalyser.
 *
 * @author aschaefer, Namics AG
 * @since 07.05.18 15:05
 */
public class SurefireReportAnalyser {

	private static final int PCENT = 100;

	public Report analyse(List<File> reportsDirectories, ConsoleLogger consoleLogger) {
		Report report = new Report();
		SurefireReportParser parser = new SurefireReportParser(reportsDirectories, Locale.ENGLISH, consoleLogger);
		try {
			List<ReportTestSuite> suites = parser.parseXMLReportFiles();

			long totalTests = 0;
			long totalErrors = 0;
			long totalFailures = 0;
			long totalSkipped = 0;
			float totalElapsedTime = 0.0f;

			for (ReportTestSuite suite : suites) {
				totalTests += suite.getNumberOfTests();
				totalErrors += suite.getNumberOfErrors();
				totalFailures += suite.getNumberOfFailures();
				totalSkipped += suite.getNumberOfSkipped();
				totalElapsedTime += suite.getTimeElapsed();
			}
			return report.totalTests(totalTests)
			             .totalErrors(totalErrors)
			             .totalFailures(totalFailures)
			             .totalSkipped(totalSkipped)
			             .totalElapsedTime(round(totalElapsedTime, 2))
			             .totalPercentage(computePercentage(totalTests, totalErrors, totalFailures, totalSkipped))
			             .testDetails(getTestDetails(suites))
					;
		} catch (MavenReportException e) {
			throw new IllegalStateException("Could not build report", e);
		}
	}

	private List<ReportTestCase> getTestDetails(List<ReportTestSuite> suites) {
		return suites.stream()
		             .flatMap(reportTestSuite -> reportTestSuite.getTestCases().stream())
		             .map(t -> new ReportTestCase()
				             .className(t.getClassName())
				             .fullClassName(t.getFullClassName())
				             .fullName(t.getFullName())
				             .name(t.getName())
				             .time(t.getTime())
				             .failureMessage(t.getFailureMessage())
				             .failureType(t.getFailureType())
				             .failureErrorLine(t.getFailureErrorLine())
				             .failureDetail(t.getFailureDetail())
				             .hasFailure(t.hasFailure())
		             )
		             .collect(toList());
	}

	protected double computePercentage(long tests, long errors, long failures, long skipped) {
		double exact = tests == 0 ? 0 : ((double) (tests - errors - failures - skipped) / (double) tests) * PCENT;
		return round(exact, 2);
	}

}
