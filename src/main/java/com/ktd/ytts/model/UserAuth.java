package com.ktd.ytts.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "user_auth")
public class UserAuth implements org.springframework.security.core.userdetails.UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "userAuth", cascade = CascadeType.ALL)
    private UserDetails userDetails;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }



    /**
     * Init user
     * */
    @PrePersist
    public void prePersist() {
        if (this.userDetails == null) {
            this.userDetails = new UserDetails();
            this.userDetails.setUserAuth(this);
        }
    }
}
