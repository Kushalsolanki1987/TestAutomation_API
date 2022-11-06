package com.restassured.cucumber.options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/restassured/features", 
plugin = {"json:target/jsonReports/cucumber-report.json", "pretty"}, 
glue = {"com.restassured.stepdefinitions"})
//,tags="@Regression and @NewIssue")

public class TestRunner {

}
