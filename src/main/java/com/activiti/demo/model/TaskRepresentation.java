package com.activiti.demo.model;

public class TaskRepresentation {
	
	private String id;
	private String name;
	
	public TaskRepresentation() {}
	
	public TaskRepresentation(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TaskRepresentation [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
