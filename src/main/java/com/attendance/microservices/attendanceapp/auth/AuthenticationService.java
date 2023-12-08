package com.attendance.microservices.attendanceapp.auth;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.config.JwtService;
import com.attendance.microservices.attendanceapp.entities.Role;
import com.attendance.microservices.attendanceapp.entities.Users;
import com.attendance.microservices.attendanceapp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(RegisterRequest request) {

        // Check if user already exists
        // If exists -> return BAD_REQUEST
        // Else add user, return token
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with the username '" + request.getUsername() + "' already exists");
        } else {
            var user = Users.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole().equals("admin") ? Role.ADMIN : Role.TEACHER)
                    .build();
            repository.save(user);

            Collection<? extends GrantedAuthority> role = user.getAuthorities();

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("role", role);
            var jwtToken = jwtService.generateToken(extraClaims, user);

            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build());
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();


        Collection<? extends GrantedAuthority> role = user.getAuthorities();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);
        var jwtToken = jwtService.generateToken(extraClaims, user);


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
