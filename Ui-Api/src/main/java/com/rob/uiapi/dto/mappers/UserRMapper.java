package com.rob.uiapi.dto.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rob.core.models.Permission;
import com.rob.core.models.Role;
import com.rob.core.models.User;
import com.rob.core.utils.java.IMapper;
import com.rob.uiapi.dto.models.UserR;

@Component
public class UserRMapper implements IMapper<User, UserR>{

	@Override
	public UserR map(User input) {
		return this.map(input, null);
	}
	
	@Override
	public UserR map(User input, UserR output) {
		if(input==null) {
			return null;
		}
		
		if(output==null) {
			output = new UserR();
		}
		
		output.setId(input.getId());
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		output.setUsername(input.getUsername());		
		output.setRoles(input.getRoles().stream().map(role -> this.mapRole(role)).collect(Collectors.toSet()));
		
		return output;
		
	}
	
	private UserR.RoleR mapRole(Role input) {
		if(input==null) {
			return null;
		}
		
		UserR.RoleR output = new UserR.RoleR();
		
		output.setId(input.getId());
		output.setName(input.getName());
		output.setPermissions(input.getPermissions().stream().map(permission -> this.mapPermission(permission)).collect(Collectors.toSet()));
		
		return output;
		
		
	}
	
	private UserR.RoleR.PermissionR mapPermission(Permission input){
		if(input==null) {
			return null;
		}
		
		UserR.RoleR.PermissionR output = new UserR.RoleR.PermissionR();
		
		output.setId(input.getId());
		output.setName(input.getName());
		output.setDescription(input.getDescription());
		
		return output;
		
	}
	
}
