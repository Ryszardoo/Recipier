package com.example.recipier.controller;

import com.example.recipier.service.SpoonacularService;
import com.example.recipier.dto.SpoonacularResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes/external")
public class ExternalRecipeController {

    private final SpoonacularService spoonacularService;

    public ExternalRecipeController(SpoonacularService spoonacularService) {
        this.spoonacularService = spoonacularService;
    }

    @GetMapping("/recipes")
    public ResponseEntity<SpoonacularResponse> getExternalRecipes(@RequestParam("query") String query) {
        return ResponseEntity.ok(spoonacularService.getRecipes(query));
    }
}
