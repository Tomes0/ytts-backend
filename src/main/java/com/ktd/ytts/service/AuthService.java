package com.ktd.ytts.service;

import com.ktd.ytts.config.PersistentUserDetailsService;
import com.ktd.ytts.dto.login.LoginRequest;
import com.ktd.ytts.dto.login.RegistrationRequest;
import com.ktd.ytts.model.UserAuth;
import com.ktd.ytts.repository.UserAuthRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserAuthRepository userAuthRepository;
    private final PersistentUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public ResponseEntity<String> register(RegistrationRequest registrationRequest) {

        if (userAuthRepository.findUserAuthByUsername(registrationRequest.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }

        UserAuth userAuth = modelMapper.map(registrationRequest, UserAuth.class);

        userDetailsService.saveUser(userAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    public ResponseEntity<String> login(LoginRequest loginRequest) {

        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

        try {
            Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
            return ResponseEntity.status(HttpStatus.OK).body("User logged in successfully");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
        }
    }
}
