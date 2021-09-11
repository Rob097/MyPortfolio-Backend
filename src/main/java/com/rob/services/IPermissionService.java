package com.rob.services;

import java.util.List;

import com.rob.models.Permission;

public interface IPermissionService {

	List<Permission> findAllByRoleId(Integer id);
	
}
