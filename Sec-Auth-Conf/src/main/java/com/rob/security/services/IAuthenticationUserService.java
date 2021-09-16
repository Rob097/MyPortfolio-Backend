package com.rob.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rob.core.models.User;


public interface IAuthenticationUserService {
	
	User save(User u);
	
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
