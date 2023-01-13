package edu.guiweid.school.dao;

import edu.guiweid.school.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository <User, Integer>{

Optional<User> findByEmail(String email);

}
