package com.ktd.ytts.config;

import com.ktd.ytts.model.UserAuthentication;
import com.ktd.ytts.repository.UserAuthRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PersistentUserDetailsService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthentication userAuthentication = userAuthRepository.findUserAuthByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return modelMapper.map(userAuthentication, UserDetails.class);
    }

    public void saveUser(UserAuthentication userAuthentication) {
        userAuthentication.setPassword(passwordEncoder.encode(userAuthentication.getPassword()));
        userAuthRepository.save(userAuthentication);
    }
}
