package com.olidev.pe.TutorialJWT.auth;

import com.olidev.pe.TutorialJWT.jwt.JwtService;
import com.olidev.pe.TutorialJWT.model.dao.UserRepository;
import com.olidev.pe.TutorialJWT.model.dto.AuthResponse;
import com.olidev.pe.TutorialJWT.model.dto.LoginDto;
import com.olidev.pe.TutorialJWT.model.dto.RegistroDto;
import com.olidev.pe.TutorialJWT.model.entity.User;
import com.olidev.pe.TutorialJWT.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginDto datos) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(datos.getEmail(), datos.getPassword()));
        UserDetails user = userRepository.findByEmail(datos.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse registro(RegistroDto datos) {

        Optional<User> userOptional = userRepository.findByEmail(datos.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        User user = User.builder()
                .email(datos.getEmail())
                .password(passwordEncoder.encode(datos.getPassword()))
                .nombre(datos.getNombre())
                .apellido(datos.getApellido())
                .pais(datos.getPais())
                .role(Role.valueOf(datos.getRol()))
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }
}
