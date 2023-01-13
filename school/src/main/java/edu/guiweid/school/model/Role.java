package edu.guiweid.school.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.guiweid.school.view.UserView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(UserView.class)
    private Integer id;
    @JsonView(UserView.class)
    private String name;


}
