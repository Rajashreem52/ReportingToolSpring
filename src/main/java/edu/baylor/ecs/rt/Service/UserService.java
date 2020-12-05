package edu.baylor.ecs.rt.Service;
import edu.baylor.ecs.rt.model.auth.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//Spring annotations, feel free to ignore it
@Repository
@Transactional
public class UserService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    PasswordEncoder encoder;
    public void saveUser(User user) {
        em.persist(user);
    }

    public void updateUser(User user) {
    	
    	
        User u = em.find(User.class, user.getId());
        if(user.getUsername()!=null && !user.getUsername().isEmpty())
        {
        	
        	u.setUsername(user.getUsername());
        }
        
        if(user.getEmail()!=null && !user.getEmail().isEmpty())
        {
        	u.setEmail(user.getEmail());
        }
        if(user.getFirstName()!=null && !user.getFirstName().isEmpty())
        {
        	 u.setFirstName(user.getFirstName());
        }
        if(user.getLastName()!=null && !user.getLastName().isEmpty())
        {
        	  u.setLastName(user.getLastName());
        }
        if(user.getPassword()!=null && !user.getPassword().isEmpty())
        {
        	 u.setPassword( encoder.encode(user.getPassword()));
        }
       
        em.persist(u);
    }

    public User findById(long id) {
        return em.find(User.class, id);
    }
}
