package com.rob.core.services;

import java.sql.SQLException;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rob.core.models.Role;
import com.rob.core.repositories.IRoleRepository;

@Service
public class RoleService implements IRoleService {

	@Autowired
    private IRoleRepository roleRepository;
	
	@Override
	public Role create(Role role) throws SQLException {
		Validate.notNull(role, "Mandatory paramete is missing: user");

		role = roleRepository.create(role);
		
		/**
		 * @TODO: Aggiungere la creazione della relazione tra ruolo e permissions 
		 */

		Validate.notNull(role, "Error creating new role.");
		
		return role;
	}

	

}
