package com.gozman.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private UserRepository userRepository;

    /*
    * route used to retrieve an access token
    * should be customized to include  bad credentials message
     */
    @GetMapping("/getToken")
    public  String getToken(@RequestParam String username, @RequestParam String password){
        return  userRepository.generateToken(username, password);
    }

    @GetMapping("/error")
    public  String error(){
        return  "error-page";
    }
}
