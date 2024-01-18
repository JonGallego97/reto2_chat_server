package com.example.reto2_chat_server.department.service;


public class DepartmentServiceModel {
    private Integer id;
    private String name;


    public DepartmentServiceModel() {
		super();
	}

	public DepartmentServiceModel(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}

