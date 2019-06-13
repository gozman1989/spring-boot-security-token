package com.gozman.security.token;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
* just a mock implementation of a class used to generate tokens for
* some hardcoded users
 */
@Repository
public class UserRepository {

    public List<String> validTokens = new ArrayList();
    private List<String> allowedUserNames = new ArrayList<>();
    {
        allowedUserNames.add("user");
        allowedUserNames.add("gozman");
    }

    public boolean validates(String token) {
        return validTokens.contains(token);
    }


    public boolean isUsernameValid(String username){
        return allowedUserNames.contains(username);
    }

    public String generateToken(String username, String password) {
        String token = UUID.randomUUID().toString();
        validTokens.add(token);
        return token;
    }
}
