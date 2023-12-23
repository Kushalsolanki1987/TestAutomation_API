package com.restassured.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public  class Utils {

	static PrintStream log;
	public static RequestSpecification requestSpec;
	public static RequestSpecification requestSpecWithoutToken;
	private static  final String PRIVATE_TOKEN_HEADER_NAME = "PRIVATE-TOKEN";
	
	static {
		try {
			log = new PrintStream(new FileOutputStream("logging.txt"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static RequestSpecification requestSpecifications(){

		/*if (requestSpec == null) {*/

			requestSpec = new RequestSpecBuilder().setBaseUri(getProperties("baseUrl")).setContentType(ContentType.JSON)
					.addHeader(PRIVATE_TOKEN_HEADER_NAME, getProperties("privateToken"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return requestSpec;
			/*
			 * }else { return requestSpec; }
			 */
		//return requestSpec;
	}

	public static  String getProperties(String value) {

		try (InputStream input = new FileInputStream("src/test/java/com/restassured/resources/config.properties")) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			return prop.getProperty(value);

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String getJsonPath(Response responseBody, String key) {

		String resp = responseBody.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}

	public static RequestSpecification requestSpecificationsWithoutToken() throws FileNotFoundException {

		if (requestSpecWithoutToken == null) {

			requestSpecWithoutToken = new RequestSpecBuilder().setBaseUri(getProperties("baseUrl"))
					.setContentType(ContentType.JSON).addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return requestSpecWithoutToken;
		}
		return requestSpecWithoutToken;
	}

}
