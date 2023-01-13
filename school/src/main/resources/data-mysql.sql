--INSERT INTO role(name) VALUES ("ROLE_REDACTEUR"),("ROLE_ADMINISTRATEUR");
--INSERT INTO user(email,password) VALUES ("a@a.com","azerty"),("b@b.com","azerty"),("c@c.com","azerty");
INSERT INTO user(email,password)
VALUES
("a@a.com","$2a$10$yXq2KL7Zr6fHgOGt7rzbZ.e3Ns1b6b16H9RHbWmsTLezA7cPXt4H6"),
("b@b.com","$2a$10$yXq2KL7Zr6fHgOGt7rzbZ.e3Ns1b6b16H9RHbWmsTLezA7cPXt4H6"),
("c@c.com","$2a$10$yXq2KL7Zr6fHgOGt7rzbZ.e3Ns1b6b16H9RHbWmsTLezA7cPXt4H6");

--INSERT INTO user_role (user_id,role_id) VALUES (1,1),(2,1),(2,2);

INSERT INTO administrateur (id,super_admin) VALUES (1,1),(3,1);

INSERT INTO module(name,description, referent_id)
VALUES
("CDA","Concepteurs développeurs d'application",1),
("DWWM","Développeur Web et Web Mobile",3);

INSERT INTO user_module( user_id, module_id) VALUES (1,1),(1,2),(2,2);