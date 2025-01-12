package com.example.recipier.controller;

import com.example.recipier.dto.RecipeDTO;
import com.example.recipier.model.Recipe;
import com.example.recipier.model.User;
import com.example.recipier.mapper.RecipeMapper;
import com.example.recipier.repository.RecipeRepository;
import com.example.recipier.repository.UserRepository;
import com.example.recipier.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    // Pobranie wszystkich przepisów
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }


    // Pobranie przepisu po ID
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(recipeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Usunięcie przepisu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        boolean isDeleted = recipeService.deleteRecipe(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Dodawanie przepisu
    @PostMapping
    public RecipeDTO addRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe.setUser(currentUser);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(savedRecipe);

    }


    // Aktualizacja przepisu
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDTO recipeDTO) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Recipe updatedRecipe = recipeService.updateRecipe(id, recipeDTO, currentUser);
        return ResponseEntity.ok(recipeMapper.toDto(updatedRecipe));
    }



}
