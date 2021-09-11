package com.rob.core.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rob.core.models.Role;

@Service
public class RoleService implements IRoleService {

	@PersistenceContext
    EntityManager entityManager;
	
	@Autowired
	IPermissionService permissionService;
	
	@Override
	public Role findById(Integer id) {
		Query query = entityManager.createNativeQuery("SELECT * FROM role em " +
                " WHERE em.id = ?", Role.class);
        query.setParameter(1, id + "%");

        Role role = null;
        
        role = (Role) query.getResultList().iterator().next();
        
        return role;
	}

	@Override
	public List<Role> findAllByUserId(Integer id) {

		Query query = entityManager.createNativeQuery("SELECT em.* FROM role em " + " inner join user_roles ur on em.id = ur.role_id " +
                " WHERE ur.user_id = ?", Role.class);
        query.setParameter(1, id + "%");
        
        List<Role> roles = new ArrayList<>();
        
        Iterator<?> listIterator = query.getResultList().listIterator();
        
        while(listIterator.hasNext()) {
        	Role role = (Role) listIterator.next();
        	
        	role.setPermissions(permissionService.findAllByRoleId(role.getId()));
        	
        	roles.add(role);
        }
        
        return roles;

	}

}
