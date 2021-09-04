package com.rob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rob.models.User;
import com.rob.repositories.IUserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
    private IUserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User save(User u) {
		return userRepository.save(u);		
	}

}
