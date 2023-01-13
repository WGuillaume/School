package edu.guiweid.school.dao;

import edu.guiweid.school.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleDao extends JpaRepository <Role, Integer>{


}
