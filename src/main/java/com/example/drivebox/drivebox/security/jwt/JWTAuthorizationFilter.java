package com.example.drivebox.drivebox.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userService;

    public JWTAuthorizationFilter(UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            var algorithm = Algorithm.HMAC256("secret code");
            var verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();

            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, ((UserDetails) userDetails).getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            throw new IllegalStateException("Failed to authenticate");
        }

        filterChain.doFilter(request, response);
    }
}
