package com.rob.core.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

import com.rob.authentication.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 7503486655601249201L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	@Column
	@Enumerated(EnumType.STRING)
	private ERole name;

	@NotBlank
	@Transient
	private List<Permission> permissions;

	public static Role byId(Integer id) {
		if (id == null) {
			return null;
		}
		
		Role role = new Role();
		role.setId(id);
		
		return role;
	}

	@Override
	public String getAuthority() {
		return ""+this.getId();
	}	
	
}
