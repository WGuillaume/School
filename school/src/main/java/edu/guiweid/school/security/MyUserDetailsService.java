package edu.guiweid.school.security;

import edu.guiweid.school.dao.AdministrateurDao;
import edu.guiweid.school.dao.UserDao;
import edu.guiweid.school.model.Administrateur;
import edu.guiweid.school.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao  userDao;
    @Autowired
    private AdministrateurDao administrateurDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userDao.findByEmail(username);
        if (user.isEmpty()){
            throw  new UsernameNotFoundException("Email inconnu");
        }

        Optional<Administrateur> admin = administrateurDao.findById(user.get().getId());
        if(admin.isPresent()){
            return new MyUserDetails(admin.get());
        }
        return new MyUserDetails(user.get());
    }
}
