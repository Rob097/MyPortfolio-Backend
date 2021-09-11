package com.rob.authentication.dto.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rob.authentication.enums.EPermission;
import com.rob.authentication.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserR implements UserDetails {

	private static final long serialVersionUID = -6128934680400004965L;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoleR implements GrantedAuthority{
		
		private static final long serialVersionUID = 7708442137300080212L;

		@Data
		private static class PermissionR{
			
			@NotBlank
			private Integer id;
			
			@NotBlank
			private EPermission name;

			private String description;
			
		}
		
		@NotBlank
		private Integer id;

		@NotBlank
		private ERole name;

		@NotBlank
		private List<PermissionR> permissions;		

		@Override
		public String getAuthority() {
			return ""+this.getId();
		}
	}
	
	
	/* CAMPI DI USERR */

	@Id
	private Integer id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;
	
	private Set<RoleR> roles = new HashSet<>();

	public UserR(
			@NotBlank @Size(max = 20) String username, 
			@NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public static RoleR roleById(Integer id) {
		if (id == null) {
			return null;
		}
		
		RoleR role = new RoleR();
		role.setId(id);
		
		return role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
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
