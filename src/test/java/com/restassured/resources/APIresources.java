package com.restassured.resources;

public enum APIresources {

	NewIssueAPI("/api/v4/projects/40765416/issues/"), GetIssueAPI("/api/v4/projects/40765416/issues/"),
	DeleteIssueAPI("/api/v4/projects/40765416/issues/"), EditIssueAPI("/api/v4/projects/40765416/issues/");

	private String resource;

	APIresources(String resource) {
		this.resource = resource;

	}

	public String getResource() {

		return resource;
	}

}
