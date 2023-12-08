package com.attendance.microservices.attendanceapp.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
//     private final UserRepository repository;
//     private final PasswordEncoder passwordEncoder;
//     private final JwtService jwtService;
//     private final AuthenticationManager authenticationManager;

//     public AuthenticationResponse register(RegisterRequest request) {
//         var user = Users.builder()
//                 .username(request.getUsername())
//                 .password(passwordEncoder.encode(request.getPassword()))
//                 .is_admin(request.getAdmin())
//                 .role(request.getAdmin() ? Role.ADMIN : Role.USER)
//                 .build();
//         repository.save(user);

        
//         Collection<? extends GrantedAuthority> role = user.getAuthorities();

//         Map<String, Object> extraClaims = new HashMap<>();
//         extraClaims.put("role", role);
//         var jwtToken = jwtService.generateToken(extraClaims, user);
//         return AuthenticationResponse.builder()
//                 .token(jwtToken)
//                 .build();
//     }


//     public AuthenticationResponse authenticate(AuthenticationRequest request) {
//         authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         request.getUsername(),
//                         request.getPassword()));
//         var user = repository.findByUsername(request.getUsername())
//                 .orElseThrow();


//         // System.out.println(user);


//         Collection<? extends GrantedAuthority> role = user.getAuthorities();

//         Map<String, Object> extraClaims = new HashMap<>();
//         extraClaims.put("role", role);
//         var jwtToken = jwtService.generateToken(extraClaims, user);


//         // System.out.println(jwtToken);

        
//         return AuthenticationResponse.builder()
//                 .token(jwtToken)
//                 .build();
//     }
}
