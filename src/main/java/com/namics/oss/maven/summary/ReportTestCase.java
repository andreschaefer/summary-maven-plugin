package com.namics.oss.maven.summary;

public class ReportTestCase {
	private String fullClassName;
	private String className;
	private String fullName;
	private String name;
	private float time;
	private String failureMessage;
	private String failureType;
	private String failureErrorLine;
	private String failureDetail;
	private boolean hasFailure;


	public ReportTestCase() {
	}


	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public String getFailureType() {
		return failureType;
	}

	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}

	public String getFailureErrorLine() {
		return failureErrorLine;
	}

	public void setFailureErrorLine(String failureErrorLine) {
		this.failureErrorLine = failureErrorLine;
	}

	public String getFailureDetail() {
		return failureDetail;
	}

	public void setFailureDetail(String failureDetail) {
		this.failureDetail = failureDetail;
	}

	public boolean isHasFailure() {
		return hasFailure;
	}

	public void setHasFailure(boolean hasFailure) {
		this.hasFailure = hasFailure;
	}


	public ReportTestCase fullClassName(String fullClassName) {
		setFullClassName(fullClassName);
		return this;
	}

	public ReportTestCase className(String className) {
		setClassName(className);
		return this;
	}

	public ReportTestCase fullName(String fullName) {
		setFullName(fullName);
		return this;
	}

	public ReportTestCase name(String name) {
		setName(name);
		return this;
	}

	public ReportTestCase time(float time) {
		setTime(time);
		return this;
	}

	public ReportTestCase failureMessage(String failureMessage) {
		setFailureMessage(failureMessage);
		return this;
	}

	public ReportTestCase failureType(String failureType) {
		setFailureType(failureType);
		return this;
	}

	public ReportTestCase failureErrorLine(String failureErrorLine) {
		setFailureErrorLine(failureErrorLine);
		return this;
	}

	public ReportTestCase failureDetail(String failureDetail) {
		setFailureDetail(failureDetail);
		return this;
	}

	public ReportTestCase hasFailure(boolean hasFailure) {
		setHasFailure(hasFailure);
		return this;
	}

	@Override
	public String toString() {
		return "[" + fullName + ":" + failureErrorLine + "] " + failureDetail;
	}
}