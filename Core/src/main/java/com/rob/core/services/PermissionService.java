package com.rob.core.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.rob.core.models.Permission;

@Service
public class PermissionService implements IPermissionService {

	@PersistenceContext
    EntityManager entityManager;
	
	@Override
	public List<Permission> findAllByRoleId(Integer id) {

		Query query = entityManager.createNativeQuery("SELECT em.* FROM permission em " + " inner join role_permissions rp on em.id = rp.permission_id " +
                " WHERE rp.role_id = ?", Permission.class);
        query.setParameter(1, id + "%");
        
        List<Permission> permissions = new ArrayList<>();
        
        Iterator<?> listIterator = query.getResultList().listIterator();
        
        while(listIterator.hasNext()) {
        	Permission permission = (Permission) listIterator.next();
        	permissions.add(permission);
        }
        
        return permissions;
        
	}

}
