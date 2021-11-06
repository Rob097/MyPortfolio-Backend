package com.rob.uiapi.dto.mappers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rob.core.models.SYS.Role;
import com.rob.core.models.SYS.User;
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
		output.setName(input.getName());
		output.setSurname(input.getSurname());
		output.setAge(input.getAge());
		output.setSex(input.getSex());
		output.setUsername(input.getUsername());
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		output.setAddress(input.getAddress());	
		output.setRoles(input.getRoles().stream().map(role -> Role.byId(role.getId())).collect(Collectors.toCollection(ArrayList::new)));
		
		return output;
		
	}
	
}
