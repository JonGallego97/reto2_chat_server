package com.example.reto2_chat_server.security.user.controller;

public class AuthPutModel {
	private Integer id;
	private String email;
	private String newPassword;
	private String name;
	private String surname1;
	private String surname2;
	private String dni;
	private String address;
	private Boolean firstLogin;
	private Integer phone1;
	private Integer phone2;
	private String photo;
	private Boolean dual;

	
	
	public AuthPutModel(Integer id, String email, String newPassword, String name, String surname1, String surname2,
			String dni, String address, Boolean firstLogin, Integer phone1, Integer phone2, String photo,
			Boolean dual) {
		super();
		this.id = id;
		this.email = email;
		this.newPassword = newPassword;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.dni = dni;
		this.address = address;
		this.firstLogin = firstLogin;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.photo = photo;
		this.dual = dual;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Boolean getDual() {
		return dual;
	}


	public void setDual(Boolean dual) {
		this.dual = dual;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname1() {
		return surname1;
	}
	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}
	public String getSurname2() {
		return surname2;
	}
	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Boolean getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	public Integer getPhone1() {
		return phone1;
	}
	public void setPhone1(Integer phone1) {
		this.phone1 = phone1;
	}
	public Integer getPhone2() {
		return phone2;
	}
	public void setPhone2(Integer phone2) {
		this.phone2 = phone2;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	



}
