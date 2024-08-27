package com.ktd.ytts.model;


import org.springframework.data.relational.core.mapping.Table;

@Entity("user_auth")
public class UserLogin {

    private Long id;
    private String username;
    private String password;
}
