package com.example.reto2_chat_server.security.user.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.reto2_chat_server.chat.service.ChatServiceModel;
import com.example.reto2_chat_server.department.service.DepartmentServiceModel;
import com.example.reto2_chat_server.model.RoleServiceModel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserServiceModel implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String email;
	private String password;
    private String name;
    private String surname1;
    private String surname2;
    private String DNI;
    private String address;
    private Integer phoneNumber1;
    private Integer phoneNumber2;
    private Boolean dual;
    private Boolean firstLogin;
    private String image;
    private List<RoleServiceModel> roles;
    private DepartmentServiceModel department;
    
   private List<ChatServiceModel> listChats;
   
   
	public List<ChatServiceModel> getListChats() {
	return listChats;
}


public UserServiceModel(Integer id, String email, String name, String surname1, String surname2
			 ) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
	}


@Override
public String toString() {
	return "UserServiceModel [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name
			+ ", surname1=" + surname1 + ", surname2=" + surname2 + ", DNI=" + DNI + ", address=" + address
			+ ", phoneNumber1=" + phoneNumber1 + ", phoneNumber2=" + phoneNumber2 + ", dual=" + dual + ", firstLogin="
			+ firstLogin + ", image=" + image + ", roles=" + roles + ", department=" + department + ", listChats="
			+ listChats + "]";
}


public void setListChats(List<ChatServiceModel> listChats) {
	this.listChats = listChats;
}


	public UserServiceModel(Integer id, String email, String name, String surname1, String surname2, String dNI,
		String address, Integer phoneNumber1, Integer phoneNumber2, Boolean dual, Boolean firstLogin,
		List<RoleServiceModel> roles, DepartmentServiceModel department, List<ChatServiceModel> listChats) {
	super();
	this.id = id;
	this.email = email;
	this.name = name;
	this.surname1 = surname1;
	this.surname2 = surname2;
	DNI = dNI;
	this.address = address;
	this.phoneNumber1 = phoneNumber1;
	this.phoneNumber2 = phoneNumber2;
	this.dual = dual;
	this.firstLogin = firstLogin;
	this.roles = roles;
	this.department = department;
	this.listChats = listChats;
}


	public UserServiceModel(Integer id, String email, String name, String surname1, String surname2, String dNI,
			String address, Integer phoneNumber1, Integer phoneNumber2, Boolean dual, Boolean firstLogin,
			List<RoleServiceModel> roles, DepartmentServiceModel department) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		DNI = dNI;
		this.address = address;
		this.phoneNumber1 = phoneNumber1;
		this.phoneNumber2 = phoneNumber2;
		this.dual = dual;
		this.firstLogin = firstLogin;
		this.roles = roles;
		this.department = department;
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}




	


	public UserServiceModel(Integer id, String email, String password, String name, String surname1, String surname2,
			String dNI, String address, Integer phoneNumber1, Integer phoneNumber2, Boolean dual, Boolean firstLogin,
			String image) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		DNI = dNI;
		this.address = address;
		this.phoneNumber1 = phoneNumber1;
		this.phoneNumber2 = phoneNumber2;
		this.dual = dual;
		this.firstLogin = firstLogin;
		this.image = image;
	}


	public UserServiceModel(Integer id, String email, String password, String name, String surname1, String surname2,
			String dNI, Integer phoneNumber1, Integer phoneNumber2, Boolean firstLogin, String image) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		DNI = dNI;
		this.phoneNumber1 = phoneNumber1;
		this.phoneNumber2 = phoneNumber2;
		this.firstLogin = firstLogin;
		this.image = image;
	}


	public UserServiceModel() {
		super();
	}



	public UserServiceModel(Integer id, String email, String name, String surname1, String surname2,
			Integer phoneNumber1) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.phoneNumber1 = phoneNumber1;
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
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getPhoneNumber1() {
		return phoneNumber1;
	}
	public void setPhoneNumber1(Integer phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	public Integer getPhoneNumber2() {
		return phoneNumber2;
	}
	public void setPhoneNumber2(Integer phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	public Boolean getDual() {
		return dual;
	}
	public void setDual(Boolean dual) {
		this.dual = dual;
	}
	public Boolean getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	public List<RoleServiceModel> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleServiceModel> roles) {
		this.roles = roles;
	}
	public DepartmentServiceModel getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentServiceModel department) {
		this.department = department;
	}



	public UserServiceModel(Integer id, String email, String name, String surname1, String surname2, String dNI,
			String address, Integer phoneNumber1, Integer phoneNumber2, Boolean dual, Boolean firstLogin,
			String image) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		DNI = dNI;
		this.address = address;
		this.phoneNumber1 = phoneNumber1;
		this.phoneNumber2 = phoneNumber2;
		this.dual = dual;
		this.firstLogin = firstLogin;
		this.image = image;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
