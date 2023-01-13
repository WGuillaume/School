package edu.guiweid.school.security;

import edu.guiweid.school.model.Administrateur;
import edu.guiweid.school.model.Module;
import edu.guiweid.school.model.Role;
import edu.guiweid.school.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class MyUserDetails implements UserDetails {


    private User user;
    public MyUserDetails(User user){
        this.user=user;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> listAuthorities = new ArrayList<>();

        if(this.user instanceof Administrateur) {
            listAuthorities.add(
                   new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR")
            );
        }


//        for(Role role  : this.user.getListRole()){
//            listAuthorities.add(
//                    new SimpleGrantedAuthority(role.getName())
//            );
//
//        }

        listAuthorities.add(
                new SimpleGrantedAuthority("ROLE_UTILISATEUR")
        );




        //-------- GESTION DES ROLES AVEC UN BOOLEEN ------
//        listAuthorities.add(
//                new SimpleGrantedAuthority(
//                        user.isAdmin() ? "ROLE_ADMINISTRATEUR" : "ROLE_UTILISATEUR"
//                )
//        );
//
        //-------- GESTION DES ROLES AVEC UN MANY TO ONE ------
//        listAuthorities.add(
//                new SimpleGrantedAuthority(
//                        user.getRole() == null ? "ROLE_UTILISATEUR" : user.getRole().getName()
//                )
//        );

        return listAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
