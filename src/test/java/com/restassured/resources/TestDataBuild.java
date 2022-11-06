package com.restassured.resources;

import com.assignment.restassured.pojo.IssuePayload;

public class TestDataBuild {
	
	public IssuePayload newIssuePayload(String title, String description, String labels) {   
		
		IssuePayload ip = new IssuePayload();
		ip.setAssignee_id(20);
		ip.setConfidential(false);
		ip.setCreated_at("2023-03-10T03:45:40Z.");
		ip.setDescription(description);
		ip.setDue_date("2016-03-19");
		ip.setId(54321);	
		ip.setLabels(labels);
		ip.setTitle(title);
		ip.setIssue_type("issue");
		
		
		return ip;
	}

}
