package com.rob.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rob.core.utils.db.QueryFactory;
import com.rob.core.utils.java.ValueObject;
import com.rob.core.utils.java.WithID;

public class User extends ValueObject implements UserDetails, WithID<Integer> {

	private static final long serialVersionUID = 8779524100086734913L;

	public static final String Table = "user";

	/** Campi previsti in tabella user */
	public enum Field {

		// LOG_ID VARCHAR(45) No
		ID("ID"),

		// USERNAME VARCHAR(45) No
		USERNAME("USERNAME"),

		// PASSWORD VARCHAR(100) No
		PASSWORD("PASSWORD"),

		// EMAIL VARCHAR(45) No
		EMAIL("EMAIL"),

		// NAME VARCHAR(45) No
		NAME("NAME"),

		// SURNAME VARCHAR(45) No
		SURNAME("SURNAME");

		private String value;

		Field(String value) {
			this.value = value;
		}

		public String getValue(String prefix) {
			return QueryFactory.getFieldName(this, prefix);
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	private Integer id;

	private String name;

	private String surname;

	private String username;

	private String password;

	private String email;

	private List<Role> roles;

	/** Costruttore oggetto */
	public User() {
		super();
	}

	/**
	 * Costuttore di classe partendo da ResultSet
	 * 
	 * @param rst
	 * @throws SQLException
	 */
	public User(ResultSet rst) throws SQLException {
		this(rst, "");
	}

	/**
	 * Costruttore oggetto dato resultSet
	 * 
	 * @param rst
	 * @param prefix
	 * @throws SQLException
	 */
	public User(ResultSet rst, String prefix) throws SQLException {
		super();

		// ID VARCHAR(45) No
		this.setId(rst.getInt(Field.ID.getValue(prefix)));
		// USERNAME VARCHAR(45) No
		this.setUsername(rst.getString(Field.USERNAME.getValue(prefix)));
		// PASSWORD VARCHAR(100) No
		this.setPassword(rst.getString(Field.PASSWORD.getValue(prefix)));
		// EMAIL VARCHAR(45) No
		this.setEmail(rst.getString(Field.EMAIL.getValue(prefix)));
		// NAME VARCHAR(45) No
		this.setName(rst.getString(Field.NAME.getValue(prefix)));
		// SURNAME VARCHAR(45) No
		this.setSurname(rst.getString(Field.SURNAME.getValue(prefix)));

	}

	public static User byId(Integer id) {
		if (id == null) {
			return null;
		}

		User user = new User();
		user.setId(id);

		return user;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void setRole(Role role) {
		if(role!=null) {
			this.roles = new ArrayList<>();
			this.roles.add(role);
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles();
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
