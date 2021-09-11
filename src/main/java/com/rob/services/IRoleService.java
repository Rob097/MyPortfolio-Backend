package com.rob.services;

import java.util.List;

import com.rob.models.Role;

public interface IRoleService {

	Role findById(Integer id);
	
	List<Role> findAllByUserId(Integer id);
	
}
