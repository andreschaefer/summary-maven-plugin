# summary-maven-plugin


System        | Status
--------------|------------------------------------------------        
CI master     | [![Build Status][travis-master]][travis-url]
CI develop    | [![Build Status][travis-develop]][travis-url]
Dependency    | [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.namics.oss.maven/summary-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.namics.oss.maven/summary-maven-plugin)


Plugin that gathers test report files creates a summery.

In multi mudole project buils each module reports its test results when the module is build.
Within environments without fancy test result parsing it is a pain to gather actual test results in long log files.
This plugin can be run after such a build to print a combined result.   

## Usage

Add maven plugin to build declaration

```xml
<build>
	<plugins>	
		<plugin>
			<groupId>com.namics.oss.maven</groupId>
			<artifactId>summary-maven-plugin</artifactId>
			<version>0.1.1</version>
		</plugin>
	</plugins>
</build>
```

Perform all tests of your project with _fail at end_ option to get all results at once. After that you can run the summary plugin to get a nice report. 

```bash
mvn clean verify -fae
# ... output

mvn summary:summary
```

Find your report like

```text
[INFO] --- summary-maven-plugin:1.0.0-SNAPSHOT:summary (default-cli) @ m-connect ---
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] |  Test Report                                                         |
[INFO] ------------------------------------------------------------------------
[WARNING] [com.namics.oss.FailingTest.somethingThatFailes:22] java.lang.AssertionError: 

Expected: <10>
     but: was <15>
	at com.namics.oss.FailingTest.somethingThatFailes(FailingTest.java:22)
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO]  	total   :        989
[INFO]  	errors  :          0
[ERROR] 	failed  :          1
[INFO]  	skipped :          8
[INFO]  	success :      99.09% in 19.06 s
[INFO] ------------------------------------------------------------------------
```
                	
[travis-master]: https://travis-ci.org/andreschaefer/summary-maven-plugin.svg?branch=master
[travis-develop]: https://travis-ci.org/andreschaefer/summary-maven-plugin.svg?branch=develop
[travis-url]: https://travis-ci.org/andreschaefer/summary-maven-plugin
