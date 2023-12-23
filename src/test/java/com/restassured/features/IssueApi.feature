Feature: Validating Issue API's 
@NewIssue @Regression 
Scenario Outline: Verify if Issue is being successfully added using NewIssueAPI. 

	Given Issue Payload with "<title>" "<description>" "<labels>" 
	When  User calls "NewIssueAPI" with "POST" http request 
	Then The API call gets status code 201 
	And "state" in the response body is "opened" 
	And "type" in the response body is "ISSUE" 
	And verify iid maps to "<title>" using "GetIssueAPI" 
	
	Examples: 
		|title   				  | description		   |labels		|
		|issues with authorisation|unable to access API|test        |
		|Test Data Issue          |Data is duplicated  |Data	    |
		|SERVER ISSUE             |ENV IS DOWN         |SERVER      |
		|ApplicationIssue123      |Preprod1 ENV is DOWN|SERVER1     |
		
		@Regression 
		Scenario Outline:
		Verify if the user is able to edit issue using Edit Issue API 
		
			Given Edit Issue Payload with "<title>" "<description>" "<labels>" 
			When  User calls "EditIssueAPI" with "PUT" http request 
			Then The API call gets status code 200 
			And verify iid maps to "<title>" using "GetIssueAPI" 
			
			Examples: 
				|title   				          | description		             |labels		   |
				|issues with authorisation update|unable to access API update   |test update      |
				|Test Data Issue Update          |Data is duplicated Update     |Data	Update     |
				|SERVER ISSUE    UPDATE          |ENV IS DOWN UPDATE            |SERVER UPDATE    |
				|ApplicationIssue123 update123   |Preprod1 ENV is DOWN update123|SERVER1 update123|
				
				@DeleteIssue @Smoke 
				Scenario: Verify if the Delete Issue API is working. 
				
					Given Delete Issue Payload 
					When  User calls "DeleteIssueAPI" with "Delete" http request 
					Then The API call gets status code 204 
					And  User calls "GetIssueAPI" with "GET" http request 
					And The API call gets status code 404 
					And "message" in the response body is "404 Not found" 
					
					
				@Regression 
				Scenario Outline:
				Verify if  New Issue API throws an error when accessed without private token. 
					Given Issue Payload with "<title>" "<description>" "<labels>" without Private Token. 
					When  User calls "NewIssueAPI" with "POST" http request 
					Then The API call gets status code 401 
					And "message" in the response body is "401 Unauthorized" 
					
					Examples: 
						|title   				          | description		             |labels		   |
						|issues with authorisation update|unable to access API update   |test update      |
						
						@DeleteIssue 
						Scenario:
						Verify if the Delete Issue API throws an error when accessed without private token. 
						
							Given Delete Issue Payload without Private Token 
							When  User calls "DeleteIssueAPI" with "Delete" http request 
							Then The API call gets status code 401 
							And "message" in the response body is "401 Unauthorized" 
							
							
						Scenario:
						Verify if the Get Issue API throws an error when called with invalid Issue ID 
							Given Get Issue Payload with invalid "abc123"
							When  User calls "GetIssueAPI" with "GET" http request 
							Then The API call gets status code 400 
							And "error" in the response body is "issue_iid is invalid" 
 