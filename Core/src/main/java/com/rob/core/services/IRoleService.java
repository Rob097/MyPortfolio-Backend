package com.rob.core.services;

import java.sql.SQLException;

import com.rob.core.models.Role;

public interface IRoleService {

	Role create(Role role) throws SQLException;
	
}
