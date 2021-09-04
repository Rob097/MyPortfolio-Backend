package com.rob.services;

import java.util.List;

import com.rob.models.User;

public interface IUserService {
	List<User> findAll();
	
	User save(User u);
}
