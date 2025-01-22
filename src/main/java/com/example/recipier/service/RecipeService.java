package com.example.recipier.service;

import com.example.recipier.dto.RecipeDTO;
import com.example.recipier.dto.SpoonacularResponse;
import com.example.recipier.model.Recipe;
import com.example.recipier.model.User;
import com.example.recipier.repository.RecipeRepository;
import com.example.recipier.repository.UserRepository;
import com.example.recipier.mapper.RecipeMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.recipier.controller.RecipeController.getRecipeDTO;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }
    // Pobranie wszystkich przepisów
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Pobranie przepisu po ID
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    // Dodanie nowego przepisu
    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        return getRecipeDTO(recipeDTO, userRepository, recipeMapper, recipeRepository);
    }

    // Aktualizacja przepisu
    public Recipe updateRecipe(Long id, RecipeDTO recipeDTO, User currentUser) {
        return recipeRepository.findById(id)
                .filter(recipe -> recipe.getUser().getId().equals(currentUser.getId()))
                .map(recipe -> {
                    recipe.setName(recipeDTO.getName());
                    recipe.setDescription(recipeDTO.getDescription());
                    return recipeRepository.save(recipe);
                })
                .orElseThrow(() -> new RuntimeException("Not allowed to update this recipe"));
    }

    // Usunięcie przepisu
    public boolean deleteRecipe(Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
