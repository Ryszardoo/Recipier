package com.example.recipier.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role = "USER";

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Zwraca mi tu liste ról
    }

    //Do innego projektu
    @Override
    public boolean isAccountNonExpired() {
        return true; // Możesz wprowadzić logikę wygasania konta
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Możesz wprowadzić logikę blokowania konta
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Możesz wprowadzić logikę wygasania poświadczeń
    }

    @Override
    public boolean isEnabled() {
        return true; // Możesz wprowadzić logikę aktywacji konta
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Handles serialization of recipes
    private List<Recipe> recipes = new ArrayList<>();


}
