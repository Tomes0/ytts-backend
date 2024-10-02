package com.ktd.ytts.service;

import com.ktd.ytts.config.PersistentUserDetailsService;
import com.ktd.ytts.dto.login.LoginRequest;
import com.ktd.ytts.dto.login.RegistrationRequest;
import com.ktd.ytts.model.UserAuthentication;
import com.ktd.ytts.repository.UserAuthenticationRepository;
import com.ktd.ytts.util.JwtTokenService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
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

    private final UserAuthenticationRepository userAuthenticationRepository;
    private final PersistentUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final ModelMapper modelMapper;

    public ResponseEntity<String> register(RegistrationRequest registrationRequest) {

        if (userAuthenticationRepository.findUserAuthByUsername(registrationRequest.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }

        UserAuthentication userAuthentication = modelMapper.map(registrationRequest, UserAuthentication.class);

        userDetailsService.saveUser(userAuthentication);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    public ResponseEntity<String> login(LoginRequest loginRequest) {

        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

        try {
            authenticationManager.authenticate(authenticationRequest);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("JWT-Session", jwtTokenService.generateJwtToken(authenticationRequest.getName()));
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("User logged in successfully");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
        }
    }
}
