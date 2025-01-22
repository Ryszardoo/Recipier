package com.example.recipier.controller;

import com.example.recipier.model.User;
import com.example.recipier.repository.UserRepository;
import com.example.recipier.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //Rejestracja użytkownika
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    //Logowanie użytkownika
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Walidacja wejściowych danych
        if (username == null || password == null) {
            throw new RuntimeException("Username and password must not be null");
        }

        // Logowanie nazwy użytkownika
        System.out.println("Szukamy użytkownika: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("Nie znaleziono użytkownika: " + username);
                    return new RuntimeException("Invalid username or password");
                });

        System.out.println("Znaleziono użytkownika: " + user);

        // Sprawdzenie hasła
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // Wygenerowanie tokena
        String token = jwtUtil.generateToken(username);

        // Zwrócenie tokena
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);
        return ResponseEntity.ok(response);
    }


    //Aktualizowanie roli użytkownika
    @PutMapping("/users/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(role.get("role").toUpperCase());
        userRepository.save(user);
        return ResponseEntity.ok("User role updated successfully");
    }




}

