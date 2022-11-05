package stepdefinitions;

import java.io.FileNotFoundException;

import io.cucumber.java.Before;

public class Hooks {

	// Below methods is created to run the Delete Issue scenario independently
	
	
	@Before("@DeleteIssue")
	public void beforeScenario() throws FileNotFoundException { 
		StepDefinitions m = new StepDefinitions();
		
		if(StepDefinitions.iid==null) {
		m.issue_payload_with("Bug", "Issue with Frontend","Dev");
		m.user_calls_with_http_request("NewIssueAPI", "POST");
		m.verify_iid_maps_to_using("Bug", "GetIssueAPI");
		
		}
		
		
		
		
	}
	
	
	
}
