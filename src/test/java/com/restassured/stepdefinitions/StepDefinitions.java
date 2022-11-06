package com.restassured.stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import com.restassured.resources.APIresources;
import com.restassured.resources.TestDataBuild;
import com.restassured.resources.Utils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinitions extends Utils {

	static RequestSpecification requestBody;
	Response responseBody;
	TestDataBuild data = new TestDataBuild();
	static String iid;

	@Given("Issue Payload with {string} {string} {string}")
	public void issue_payload_with(String title, String description, String labels) throws FileNotFoundException {

		requestBody = given().spec(requestSpecifications()).body(data.newIssuePayload(title, description, labels));
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {

		// Constructor will be called with the value of resource which you pass
		APIresources resourceAPI = APIresources.valueOf(resource);

		if (method.equalsIgnoreCase("POST"))
			responseBody = requestBody.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			responseBody = requestBody.when().get(resourceAPI.getResource() + "{iid}");
		else if (method.equalsIgnoreCase("DELETE"))
			responseBody = requestBody.when().delete(resourceAPI.getResource() + "{iid}");
		else if (method.equalsIgnoreCase("PUT"))
			responseBody = requestBody.when().put(resourceAPI.getResource() + "{iid}");

	}

	@Then("The API call gets status code {int}")
	public void the_api_call_gets_status_code(int expectedStatuscode) {

		assertEquals(responseBody.getStatusCode(), expectedStatuscode);

	}

	@Then("{string} in the response body is {string}")
	public void in_the_response_body_is(String keyValue, String Expectedvalue) {

		assertEquals(getJsonPath(responseBody, keyValue), Expectedvalue);

	}

	@Then("verify iid maps to {string} using {string}")
	public void verify_iid_maps_to_using(String expectedTitle, String resource) throws FileNotFoundException {

		iid = getJsonPath(responseBody, "iid");
		requestBody = given().spec(requestSpecifications()).pathParam("iid", iid);
		user_calls_with_http_request(resource, "GET");
		String actualTitle = getJsonPath(responseBody, "title");
		assertEquals(actualTitle, expectedTitle);

	}

	@Given("Delete Issue Payload")
	public void delete_issue_payload() throws FileNotFoundException {
		requestBody = given().spec(requestSpecifications()).pathParam("iid", iid);

	}

	@Given("Edit Issue Payload with {string} {string} {string}")
	public void edit_issue_payload_with(String title, String description, String labels) throws FileNotFoundException {
		requestBody = given().spec(requestSpecifications()).body(data.newIssuePayload(title, description, labels))
				.pathParam("iid", iid);
	}

	@Given("Issue Payload with {string} {string} {string} without Private Token.")
	public void issue_payload_without_private_token(String title, String description, String labels)
			throws FileNotFoundException {
		requestBody = given().spec(requestSpecificationsWithoutToken())
				.body(data.newIssuePayload(title, description, labels));
	}

	@Given("Delete Issue Payload without Private Token")
	public void delete_issue_payload_without_private_token() throws FileNotFoundException {
		requestBody = given().spec(requestSpecificationsWithoutToken()).pathParam("iid", iid);
	}

	@Given("Get Issue Payload with invalid Issue Id")
	public void get_issue_payload_with_invalid_issue_id() throws FileNotFoundException {
		String iid = "abc123";
		requestBody = given().spec(requestSpecifications()).pathParam("iid", iid);
	}

}
