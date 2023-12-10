package com.example.drivebox.drivebox.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public LoginController(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }



    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

    var auth = new UsernamePasswordAuthenticationToken(username, password);
        var result = authenticationProvider.authenticate(auth);

        if (result.isAuthenticated()) {
            var algoritm = Algorithm.HMAC256("secret code");
            var token = JWT.create()
                    .withSubject(username)
                    .withIssuer("auth0")
                    .withClaim("husdjur", "hund")
                    .sign(algoritm);

            return token;
        }

        return "Failed to login.";
    }
}
