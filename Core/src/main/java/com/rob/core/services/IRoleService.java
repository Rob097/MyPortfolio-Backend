package com.rob.core.services;

import java.util.List;

import com.rob.core.models.Role;

public interface IRoleService {

	Role findById(Integer id);
	
	List<Role> findAllByUserId(Integer id);
	
}
