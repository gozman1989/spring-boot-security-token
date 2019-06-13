package com.gozman.security.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TokenAuthentificationFilter extends AbstractAuthenticationProcessingFilter {

    public final static String TOKEN_KEY = "token";

    public TokenAuthentificationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
        /*
        * the default AuthenticationSuccessHandler will redirect our user to the "/" url
        * and we don't want this for token autentification
         */
        setAuthenticationSuccessHandler((request, response, authentication) -> { });
    }

    /*
    * we need to override this in order for this filter to not block the filter chain
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }


    /*
    * this method is used inside the logic of AbstractAuthenticationProcessingFilter.doFilter
    * for getting the authentification response from our authentification manager
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String token = obtainToken(request);
        if (token == null){
            token="";
        }
        token = token.trim();

        TokenAuthentification authRequest = new TokenAuthentification(token);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    private String obtainToken(HttpServletRequest request) {
        return request.getParameter(TOKEN_KEY);
    }



}
