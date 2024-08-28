package com.ktd.ytts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_authority")
@ToString(exclude = {"userAuthentication"})
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_authentication_id", nullable = false)
    private UserAuthentication userAuthentication;

    @Enumerated(EnumType.STRING)
    private com.ktd.ytts.enums.UserAuthority userAuthority;
}
