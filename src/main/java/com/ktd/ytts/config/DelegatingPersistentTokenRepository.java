package com.ktd.ytts.config;

import com.ktd.ytts.model.UserAuthentication;
import com.ktd.ytts.repository.UserAuthenticationRepository;
import com.ktd.ytts.util.JwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

@AllArgsConstructor
public class DelegatingPersistentTokenRepository implements PersistentTokenRepository {

    private JwtTokenService jwtTokenService;
    private PersistentUserDetailsService persistentUserDetailsService;
    private UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        UserAuthentication userAuthentication =
                userAuthenticationRepository
                        .findUserAuthByUsername(token.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("No user with name: " + token.getUsername()));
        String jwt = jwtTokenService.generateJwtToken(token.getUsername());
        userAuthentication.setJwtToken(jwt);
        userAuthenticationRepository.save(userAuthentication);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String authenticationId) {


        return null;
    }

    @Override
    public void removeUserTokens(String username) {

    }
}
