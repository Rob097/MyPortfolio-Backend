package com.rob.core.services;

import java.util.List;

import com.rob.core.models.Permission;

public interface IPermissionService {

	List<Permission> findAllByRoleId(Integer id);
	
}
