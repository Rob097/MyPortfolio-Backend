package com.rob.uiapi.dto.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserR implements UserDetails {
	
private static final long serialVersionUID = -6128934680400004965L;	

	public static class RoleR implements GrantedAuthority{
		
		private static final long serialVersionUID = 7708442137300080212L;		

		public static class PermissionR{		
			public PermissionR() {
				super();
			}			

			
			private Integer id;

			private String name;

			private String description;

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

			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}		
			
		}
		
		public RoleR() {
			super();
		}
		
		private Integer id;

		private String name;

		private Set<PermissionR> permissions;

		@Override
		public String getAuthority() {
			return ""+id;
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

		public Set<PermissionR> getPermissions() {
			return permissions;
		}
		public void setPermissions(Set<PermissionR> permissions) {
			this.permissions = permissions;
		}
		
	}
	
	/* CAMPI DI USERR */

	public UserR() {
		super();
	}

	private Integer id;

	private String username;

	private String email;

	private String password;
	
	private Set<RoleR> roles = new HashSet<>();

	public UserR(
			String username, 
			String email,
			String password) {
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

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
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

	public Set<RoleR> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleR> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
}
