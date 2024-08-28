package com.ktd.ytts.controller;

import com.ktd.ytts.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ktd.ytts.dto.login.RegistrationRequest;
import com.ktd.ytts.dto.login.LoginRequest;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        return authService.register(registrationRequest);
    }

}
