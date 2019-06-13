package com.gozman.security.config;

import com.gozman.security.token.TokenAuthentificationFilter;
import com.gozman.security.token.TokenAuthentificationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;


@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private TokenAuthentificationProvider tokenAuthentificationProvider;




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/login", "/login-error", "/public", "/login2", "/error").permitAll()
                .and()
                .logout().disable();//there's no actually logout functionality for token, it can only expire but this detail is not handled by spring security directly



        TokenAuthentificationFilter tokenAuthentificationFilter =  new TokenAuthentificationFilter(new AntPathRequestMatcher("/**"));
        AuthenticationManager authenticationManager = new ProviderManager(Arrays.asList(tokenAuthentificationProvider));
        tokenAuthentificationFilter.setAuthenticationManager(authenticationManager);
        tokenAuthentificationFilter.setAllowSessionCreation(false);

        /*
        * replace and existing filter in spring boot security with our custom one
         */
        http.addFilterAt(tokenAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*
    * we are ignoring getToken as the token will be generated outside the spring security context
    * so it needs to be accesible to an unauthentificated user
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/getToken", "/error");
        super.configure(web);
    }





}