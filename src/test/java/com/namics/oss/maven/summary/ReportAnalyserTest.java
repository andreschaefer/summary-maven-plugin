/*
 * Copyright 2000-2018 Namics AG. All rights reserved.
 */

package com.namics.oss.maven.summary;

import org.apache.maven.plugin.surefire.log.api.ConsoleLogger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * ReportAnalyserTest.
 *
 * @author aschaefer, Namics AG
 * @since 07.05.18 15:39
 */
public class ReportAnalyserTest {

	@Test
	public void sample() throws IOException {
		Report report = new SurefireReportAnalyser().analyse(asList(new File(getClass().getResource("/reports").getFile())),
		                                                     mock(ConsoleLogger.class));
		assertThat(report, hasProperty("totalTests", is(957L)));
		assertThat(report, hasProperty("totalFailures", is(1L)));
		assertThat(report, hasProperty("totalErrors", is(1L)));
		assertThat(report, hasProperty("totalSkipped", is(7L)));

		System.out.println(report);
	}

	@Test
	public void isErrors() {
		assertFalse(new Report().isErrors());
		assertTrue(new Report().totalErrors(4).isErrors());
	}
	
	@Test
	public void isFailures() {
		assertFalse(new Report().isFailures());
		assertTrue(new Report().totalFailures(4).isFailures());
	}
}