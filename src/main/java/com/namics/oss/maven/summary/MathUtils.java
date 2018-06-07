/*
 * Copyright 2000-2018 Namics AG. All rights reserved.
 */

package com.namics.oss.maven.summary;

/**
 * MathUtils.
 *
 * @author aschaefer, Namics AG
 * @since 08.05.18 09:37
 */
public abstract class MathUtils {
	public static double round(double exact, int digits) {
		double factor = Math.pow(10,digits);
		return Math.round(exact * factor) / factor;
	}

	private MathUtils() {
		// util
	}
}
