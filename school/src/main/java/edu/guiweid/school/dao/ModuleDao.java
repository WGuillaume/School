package edu.guiweid.school.dao;

import edu.guiweid.school.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ModuleDao extends JpaRepository <Module, Integer>{


}
