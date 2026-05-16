package cl.duoc.proyectotechstore.controller;

import cl.duoc.proyectotechstore.dto.AuthRequest;
import cl.duoc.proyectotechstore.dto.AuthResponse;
import cl.duoc.proyectotechstore.security.jwt.JwtService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        String token =
                jwtService.generateToken(request.getUsername());

        return new AuthResponse(token);
    }
}