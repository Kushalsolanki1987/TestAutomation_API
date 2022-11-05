package resources;

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

public class Utils {

	public static RequestSpecification requestSpec;
	static PrintStream log;
	
	public static RequestSpecification requestSpecWithoutToken;
	private static final String PRIVATE_TOKEN_HEADER_NAME = "PRIVATE-TOKEN";

	public RequestSpecification requestSpecifications() throws FileNotFoundException {

		if (requestSpec == null) {
			log = new PrintStream(new FileOutputStream("logging.txt"));

			requestSpec = new RequestSpecBuilder().setBaseUri(getProperties("baseUrl")).setContentType(ContentType.JSON)
					.addHeader(PRIVATE_TOKEN_HEADER_NAME, getProperties("privateToken"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return requestSpec;
		}
		return requestSpec;
	}

	public String getProperties(String value) {

		try (InputStream input = new FileInputStream("src/test/java/resources/config.properties")) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			return prop.getProperty(value);

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String getJsonPath(Response responseBody, String key) {

		String resp = responseBody.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}

	public RequestSpecification requestSpecificationsWithoutToken() throws FileNotFoundException {

		if (requestSpecWithoutToken == null) {

			 log = new PrintStream(new FileOutputStream("logging.txt"));
			requestSpecWithoutToken = new RequestSpecBuilder().setBaseUri(getProperties("baseUrl"))
					.setContentType(ContentType.JSON).addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return requestSpecWithoutToken;
		}
		return requestSpecWithoutToken;
	}

}
