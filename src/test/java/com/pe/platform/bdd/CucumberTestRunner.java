package com.pe.platform.bdd;

import org.junit.platform.suite.api.*;

@Suite
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "com.pe.platform.bdd")
public class CucumberTestRunner {}
