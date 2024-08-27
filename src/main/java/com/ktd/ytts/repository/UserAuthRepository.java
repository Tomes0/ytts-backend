package com.ktd.ytts.repository;

import com.ktd.ytts.model.UserAuth;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserAuthRepository extends Repository<UserAuth, Long> {

    Optional<UserAuth> findByUsername(String username);

    void save(UserAuth userAuth);
}
