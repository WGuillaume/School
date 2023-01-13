package edu.guiweid.school.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.guiweid.school.dao.UserDao;
import edu.guiweid.school.model.User;
import edu.guiweid.school.security.JwtUtils;
import edu.guiweid.school.security.MyUserDetails;
import edu.guiweid.school.security.MyUserDetailsService;
import edu.guiweid.school.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class UserController {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDao userDao ;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @GetMapping("/users")
    @JsonView(UserView.class)
    public List<User>getAllUser() {
        return userDao.findAll();
    }

    @GetMapping("/")
    public String home(){
        return  "le serveur marche bien, mais il n'y a rien a voir sur cette route";
    }
    @GetMapping("/user/{id}")
    @JsonView(UserView.class)
    public ResponseEntity<User> getUserById(@PathVariable int id){
        Optional<User> user = userDao.findById(id);
        if (user.isPresent()) {

            return new ResponseEntity<>(user.get(), HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/connection")
    public  ResponseEntity<String> connection(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword()
            ));
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        MyUserDetails userDetails = (MyUserDetails) myUserDetailsService
                .loadUserByUsername(user.getEmail());


        return new ResponseEntity<>(jwtUtils.generateJwt(userDetails),HttpStatus.OK);


    }
    @PostMapping("/admin/user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        //si l'utilisateur fourni n'a pas toutes les informations
        if (user == null || user.getEmail().equals("") || user.getPassword().equals("")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //si l'id est fourni et qu'il n'existe pas dans la base donnée
        if(user.getId() !=null){
            Optional<User> userDatabase=userDao.findById(user.getId());
            //si il n'existe pas dans la base de donnée
            if (userDatabase.isEmpty()){
                return new ResponseEntity<>(user ,HttpStatus.BAD_REQUEST);
            }
            //l'id n'est pas fourni c'est donc un update
            user.setPassword(userDatabase.get().getPassword());
            userDao.save(user);
            return new ResponseEntity<>(user ,HttpStatus.OK);

        }
        //l'id n'est pas fourni c'est donc un CREATE
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);

        return new ResponseEntity<>(user ,HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        Optional<User> user = userDao.findById(id);

        if (user.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
         userDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
