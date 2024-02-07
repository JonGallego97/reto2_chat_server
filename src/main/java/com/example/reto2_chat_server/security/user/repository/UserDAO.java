package com.example.reto2_chat_server.security.user.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.reto2_chat_server.department.repository.DepartmentDAO;
import com.example.reto2_chat_server.department.service.DepartmentServiceModel;
import com.example.reto2_chat_server.model.Role;
import com.example.reto2_chat_server.model.RoleServiceModel;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class UserDAO implements UserDetails {
	public UserDAO(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="name")
	private String name;
	@Column(name="surname1")
	private String surname1;
	@Column(name="surname2")
	private String surname2;
	@Column(name="DNI")
	private String DNI;
	@Column(name="address")
	private String address;
	@Column(name="phoneNumber1")
	private Integer phoneNumber1;
	@Column(name="phoneNumber2")
	private Integer phoneNumber2;
	@JsonIgnore
	@Column(name="image")
	private String image;
	@JsonIgnore
	@Column(name="is_dual")
	private Boolean dual;
	@Column(name="firstLogin")
	private Boolean firstLogin;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "role_users",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"
					),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id")
			)
	@JsonIgnore
	private List<Role> listRoles;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private DepartmentDAO department;



	public UserDAO(Integer id, String email, String password, String name, String surname1, String surname2, String dNI,
			String address, Integer phoneNumber1, Integer phoneNumber2, String image, Boolean dual, Boolean firstLogin,
			List<Role> listRoles, DepartmentDAO department) {
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
		this.image = image;
		this.dual = dual;
		this.firstLogin = firstLogin;
		this.listRoles = listRoles;
		this.department = department;
	}



	public UserDAO() {
		// TODO Auto-generated constructor stub
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public DepartmentDAO getDepartment() {
		return department;
	}



	public void setDepartment(DepartmentDAO department) {
		this.department = department;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean isDual() {
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

	public List<Role> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<Role> listRoles) {
		this.listRoles = listRoles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		final List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role: listRoles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
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





	public UserDAO(Integer id, String email, String password, String name, String surname1, String surname2, String dNI,
			Integer phoneNumber1, Integer phoneNumber2, String image, Boolean firstLogin) {
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
		this.image = image;
		this.firstLogin = firstLogin;
	}



	public UserServiceModel convertFromDAOtoService(UserDAO userDAO) {


		List <RoleServiceModel> listRoles = new ArrayList<RoleServiceModel>();

		UserServiceModel response = new UserServiceModel(
				userDAO.getId(),
				userDAO.getEmail(),
				userDAO.getName(),
				userDAO.getSurname1(),
				userDAO.getSurname2(),
				userDAO.getDNI(),
				userDAO.getAddress(),
				userDAO.getPhoneNumber1(),
				userDAO.getPhoneNumber2(),
				userDAO.isDual(),
				userDAO.getFirstLogin(),
				userDAO.getImage());

		try {
			for(Role role : userDAO.getListRoles()) {
				RoleServiceModel roleServiceModel = new RoleServiceModel(role.getId(), role.getName());
				listRoles.add(roleServiceModel);


			}
			response.setRoles(listRoles);

		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		if(userDAO.getDepartment() != null) {
			DepartmentServiceModel departmentServiceModel = new DepartmentServiceModel(
					userDAO.getDepartment().getId(),
					userDAO.getDepartment().getName());
			response.setDepartment(departmentServiceModel);
		}

		return response;


	}

	public UserServiceModel convertFromDAOtoServiceResumedForMessages(UserDAO userDAO) {

		UserServiceModel response = new UserServiceModel(
				userDAO.getId(),
				userDAO.getEmail(),
				userDAO.getName(),
				userDAO.getSurname1(),
				userDAO.getSurname2(),
				userDAO.getPhoneNumber1());
		return response;




	}



	public UserDAO(Integer id, String email, String password, String name, String surname1, String surname2, String dNI,
			String address, Integer phoneNumber1, Integer phoneNumber2, String image, Boolean dual,
			Boolean firstLogin) {
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
		this.image = image;
		this.dual = dual;
		this.firstLogin = firstLogin;
	}



	@Override
	public String toString() {
		return "UserDAO [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", surname1="
				+ surname1 + ", surname2=" + surname2 + ", DNI=" + DNI + ", address=" + address + ", phoneNumber1="
				+ phoneNumber1 + ", phoneNumber2=" + phoneNumber2 + ", image=" + image + ", dual=" + dual + ", firstLogin="
				+ firstLogin + ", listRoles=" + listRoles + ", department=" + department + "]";
	}

}
