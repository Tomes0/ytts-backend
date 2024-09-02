package com.ktd.ytts.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "user_details")
@ToString(exclude = {"userAuthentication"})
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    private UserAuthentication userAuthentication;


    private String emailAddress;
}
