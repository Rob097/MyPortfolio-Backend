package com.rob.uiapi.dto.mappers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rob.core.models.Role;
import com.rob.core.models.User;
import com.rob.core.utils.java.IMapper;
import com.rob.uiapi.dto.models.UserR;

@Component
public class UserMapper implements IMapper<UserR, User>{

	@Override
	public User map(UserR input) {
		return this.map(input, null);
	}
	
	@Override
	public User map(UserR input, User output) {
		if(input==null) {
			return null;
		}
		
		if(output==null) {
			output = new User();
		}
		
		output.setId(input.getId());
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		output.setUsername(input.getUsername());
		output.setName("Name");
		output.setSurname("Surname");
		output.setRoles(input.getRoles().stream().map(role -> Role.byId(role.getId())).collect(Collectors.toCollection(ArrayList::new)));
		
		return output;
		
	}
	
}
