package com.rob.authentication.dto.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rob.authentication.dto.models.UserR;
import com.rob.authentication.models.User;

@Component
public class UserRMapper {

	public UserR map(User input) {
		return this.map(input, null);
	}
	
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
		output.setRoles(input.getRolesId().stream().map(roleId -> UserR.roleById(roleId)).collect(Collectors.toSet()));
		
		return output;
		
	}
	
}
