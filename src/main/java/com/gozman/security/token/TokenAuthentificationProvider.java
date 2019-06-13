package com.gozman.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TokenAuthentificationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String token = authentication.getName();
       if (userRepository.validates(token)){
           List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
           grantedAuthorityList.add(new SimpleGrantedAuthority("USER"));
           return  new TokenAuthentification("username", "credentials-if-any", token, grantedAuthorityList);
       }else {
           return null;
       }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(TokenAuthentification.class);
    }
}
