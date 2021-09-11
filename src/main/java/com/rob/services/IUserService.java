package com.rob.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rob.models.User;

public interface IUserService {
	List<User> findAll();
	
	User save(User u);
	
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
