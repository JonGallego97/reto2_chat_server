package com.example.reto2_chat_server.model;


public class RoleServiceModel {
	private Integer id;
	private String name;
	public RoleServiceModel(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public RoleServiceModel() {
		super();
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
