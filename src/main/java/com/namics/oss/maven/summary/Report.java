/*
 * Copyright 2000-2018 Namics AG. All rights reserved.
 */

package com.namics.oss.maven.summary;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Report.
 *
 * @author aschaefer, Namics AG
 * @since 07.05.18 14:50
 */
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	private long totalTests;
	private long totalErrors;
	private long totalFailures;
	private long totalSkipped;
	private double totalElapsedTime;
	private double totalPercentage;
	private List<ReportTestCase> testDetails;

	public boolean isErrors() {
		return totalErrors > 0;
	}

	public boolean isFailures() {
		return totalFailures > 0;
	}

	public boolean isSkipped() {
		return totalSkipped > 0;
	}


	public void setTotalTests(long totalTests) {
		this.totalTests = totalTests;
	}

	public Report totalTests(long totalTests) {
		setTotalTests(totalTests);
		return this;
	}

	public void setTotalErrors(long totalErrors) {
		this.totalErrors = totalErrors;
	}

	public Report totalErrors(long totalErrors) {
		setTotalErrors(totalErrors);
		return this;
	}

	public void setTotalFailures(long totalFailures) {
		this.totalFailures = totalFailures;
	}

	public Report totalFailures(long totalFailures) {
		setTotalFailures(totalFailures);
		return this;
	}

	public void setTotalSkipped(long totalSkipped) {
		this.totalSkipped = totalSkipped;
	}

	public Report totalSkipped(long totalSkipped) {
		setTotalSkipped(totalSkipped);
		return this;
	}

	public void setTotalElapsedTime(double totalElapsedTime) {
		this.totalElapsedTime = totalElapsedTime;
	}

	public Report totalElapsedTime(double totalElapsedTime) {
		setTotalElapsedTime(totalElapsedTime);
		return this;
	}

	public void setTotalPercentage(double totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public Report totalPercentage(double totalPercentage) {
		setTotalPercentage(totalPercentage);
		return this;
	}

	public void setTestDetails(List<ReportTestCase> testDetails) {
		this.testDetails = testDetails;
	}

	public Report testDetails(List<ReportTestCase> testDetails) {
		setTestDetails(testDetails);
		return this;
	}

	public long getTotalTests() {
		return totalTests;
	}

	public long getTotalErrors() {
		return totalErrors;
	}

	public long getTotalFailures() {
		return totalFailures;
	}

	public long getTotalSkipped() {
		return totalSkipped;
	}

	public double getTotalElapsedTime() {
		return totalElapsedTime;
	}

	public double getTotalPercentage() {
		return totalPercentage;
	}


	public List<ReportTestCase> getTestDetails() {
		return testDetails;
	}

	public List<ReportTestCase> getFailedTests() {
		return this.getTestDetails().stream()
		           .filter(ReportTestCase::isHasFailure)
		           .filter(c -> !"skipped".equalsIgnoreCase(c.getFailureType()))
		           .collect(Collectors.toList());
	}

	@Override
	public String toString() {
		StringBuilder print = new StringBuilder();
		print.append("\n------------------------------------------------------------------------");
		print.append("\n|  Test Report                                                         |");
		print.append("\n------------------------------------------------------------------------");
		this.getFailedTests().forEach(t -> print.append("\n").append(t.toString()));
		print.append("\n------------------------------------------------------------------------");
		print.append(format("\n \ttotal   : %10s", this.getTotalTests()));
		print.append(format("\n \terrors  : %10s", this.getTotalErrors()));
		print.append(format("\n \tfailed  : %10s", this.getTotalFailures()));
		print.append(format("\n \tskipped : %10s", this.getTotalSkipped()));
		print.append(format("\n \tsuccess : %10.2f%% in %4.2f s", this.getTotalPercentage(), this.getTotalElapsedTime()));
		print.append("\n------------------------------------------------------------------------");
		return print.toString();
	}
}
