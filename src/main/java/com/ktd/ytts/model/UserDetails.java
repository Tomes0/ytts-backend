package com.ktd.ytts.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_id", referencedColumnName = "id")
    private UserAuth userAuth;

    private String emailAddress;


}
