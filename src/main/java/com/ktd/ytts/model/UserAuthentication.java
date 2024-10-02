package com.ktd.ytts.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "user_authentication")
public class UserAuthentication implements org.springframework.security.core.userdetails.UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "userAuthentication", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private UserDetail userDetails;

    @OneToMany(mappedBy = "userAuthentication", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserAuthority> userAuthorities;

    private String jwtToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuthorities
                .stream()
                .map(userAuthority -> new SimpleGrantedAuthority(userAuthority.getUserAuthority().toString()))
                .collect(Collectors.toList());
    }

    @PrePersist
    public void prePersist() {
        if (this.userDetails == null) {
            this.userDetails = new UserDetail();
            this.userDetails.setUserAuthentication(this);
        } else if (this.userDetails.getUserAuthentication() == null) {
            this.userDetails.setUserAuthentication(this);
        } else if (!this.userDetails.getId().equals(this.id)) {
            throw new IllegalStateException("UserDetails ID does not match UserAuthentication ID.");
        }
    }
}
