package com.rob.uiapi.dto.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

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
		output.setRoles(input.getRolesId().stream().map(roleId -> UserR.roleById(roleId)).collect(Collectors.toSet()));
		
		return output;
		
	}
	
}
