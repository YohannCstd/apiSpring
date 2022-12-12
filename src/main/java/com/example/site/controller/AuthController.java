package com.example.site.controller;

import com.example.site.auth.JWTGenerator;
import com.example.site.dto.AuthResponseDTO;
import com.example.site.dto.LoginDto;
import com.example.site.dto.RegisterDto;
import com.example.site.entity.User;
import com.example.site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller qui permet de gérer l'authentification d'un utilisateur
 * */
@RestController
@RequestMapping("/api")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }
    /**
     * Methode qui permet d'avoir un token pour un utilisateur (username,password)
     * @param loginDto
     * @return token for user
     * */
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    /**
     * Méthode qui permet d'ajouter un nouveau utilisateur dans la base de données
     * @param registerDto
     * @return String avec le message associé
     * */
    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(registerDto.getUsername().length() > 50) return new ResponseEntity<>("Username too long (50 characters)",HttpStatus.BAD_REQUEST);
        if(registerDto.getPassword().length() > 300) return new ResponseEntity<>("Password too long (300 characters)",HttpStatus.BAD_REQUEST);
        if(registerDto.getUsername() == null || registerDto.getPassword() == null) return new ResponseEntity<>("Username or password null !",HttpStatus.BAD_REQUEST);
        if(registerDto.getUsername().length() == 0 || registerDto.getPassword().length() == 0) return new ResponseEntity<>("Username or password empty !",HttpStatus.BAD_REQUEST);

        if (userRepository.findByName(registerDto.getUsername().toLowerCase()) != null) {
            if(userRepository.findByName(registerDto.getUsername().toLowerCase()).getName().equals(registerDto.getUsername().toLowerCase())) return new ResponseEntity<>("Username is already taken!",HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByName(registerDto.getUsername())) return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        userRepository.save(new User(registerDto.getUsername().toLowerCase(),passwordEncoder.encode(registerDto.getPassword())));
        return new ResponseEntity<>("Successfully registered user!", HttpStatus.OK);
    }
}
