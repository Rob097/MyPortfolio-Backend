package com.rob.dto.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rob.dto.models.UserR;
import com.rob.models.User;

@Component
public class UserMapper {

	public User map(UserR input) {
		return this.map(input, null);
	}
	
	public User map(UserR input, User output) {
		if(input==null) {
			return null;
		}
		
		if(output==null) {
			output = new User();
		}
		
		//output.setId(""+input.getId());
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		output.setUsername(input.getUsername());
		output.setName("Name");
		output.setSurname("Surname");
		output.setRolesId(input.getRoles().stream().map(role -> role.getId()).collect(Collectors.toSet()));
		
		return output;
		
	}
	
}
