package com.assignment.restassured.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * date :- 
 * desc :- 
 * author :- Kushal Solanki
 */
@Data
public class IssuePayload {

	//Defining Variable 
	private String title;
	private String description;
	private int assignee_id;
	private boolean confidential;
	private String created_at;
	private int id;
	private String labels;
	private String due_date;
	private String issue_type;


}