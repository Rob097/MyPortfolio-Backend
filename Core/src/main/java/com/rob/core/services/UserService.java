package com.rob.core.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rob.core.repositories.IUserRepository;
import com.rob.authentication.dto.mappers.UserRMapper;
import com.rob.authentication.dto.models.UserR;
import com.rob.authentication.models.User;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
    private IUserRepository userRepository;
	
	@Autowired
    private UserRMapper userRMapper;
	
	@PersistenceContext
    EntityManager entityManager;
	
	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User save(User u) {
		return userRepository.save(u);		
	}
	
	@Transactional
	public User loadUserById(Integer id) throws UsernameNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

		return user;
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

        UserR userR = null;
        
        if(user != null){
        	userR = userRMapper.map(user);
        }
        
		return userR;
	}

}
