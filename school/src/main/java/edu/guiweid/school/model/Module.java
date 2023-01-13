package edu.guiweid.school.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.guiweid.school.view.ModuleView;
import edu.guiweid.school.view.UserView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({UserView.class,ModuleView.class})
    private Integer id;
    @JsonView({UserView.class,ModuleView.class})
    private String name;
    @JsonView({UserView.class,ModuleView.class})
    private String description;


    @ManyToMany(mappedBy = "listModule")
    Set<User> listUser = new HashSet<>();

    @ManyToOne
    Administrateur referent;

}
