package com.gozman.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class TokenAuthentification  extends AbstractAuthenticationToken {


    private final Object principal;
    private Object credentials;
    private String token;

    public TokenAuthentification(String token) {
        super(new ArrayList<>());
        this.principal = token;
        this.setAuthenticated(false);
    }

    public TokenAuthentification(Object principal, Object credentials, String token,
                                 Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.token = token;
        this.setAuthenticated(true);

    }




    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getToken() {
        return token;
    }
}
