package com.rob.core.services;

import java.sql.SQLException;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rob.core.models.Permission;
import com.rob.core.repositories.IPermissionRepository;

@Service
public class PermissionService implements IPermissionService {
	
	@Autowired
    private IPermissionRepository permissionRepository;

	@Override
	public Permission create(Permission permission) throws SQLException {
		Validate.notNull(permission, "Mandatory paramete is missing: user");

		permission = permissionRepository.create(permission);

		Validate.notNull(permission, "Error creating new permission.");
		
		return permission;
	}


}
