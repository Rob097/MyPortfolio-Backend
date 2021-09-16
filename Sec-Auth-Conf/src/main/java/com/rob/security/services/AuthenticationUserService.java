package com.rob.security.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rob.core.models.User;
import com.rob.core.repositories.IUserRepository;


/**
 * @author Roberto97
 * Service used to manage users in signin and signup
 */
@Service
public class AuthenticationUserService implements IAuthenticationUserService, UserDetailsService {

	@Autowired
    private IUserRepository userRepository;
	
	@PersistenceContext
    EntityManager entityManager;

	@Override
	public User save(User u) {
		return userRepository.save(u);		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*Query query = entityManager.createNativeQuery("SELECT id, username,password,email,name,surname,user_id,rol.role_id,permission_id FROM user em " + " left join user_roles rol on em.id = rol.user_id " + " left join role_permissions per on rol.role_id = per.role_id " +
                " WHERE em.username LIKE ?", User.class);*/
		Query query = entityManager.createNativeQuery("SELECT * FROM user em " +
                " WHERE em.username LIKE ?", User.class);
        query.setParameter(1, username + "%");

        User user = null;
        
        user = (User) query.getResultList().iterator().next();
        
		return user;
	}

}
