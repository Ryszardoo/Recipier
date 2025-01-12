package com.example.recipier.service;

import com.example.recipier.APIs.SpoonacularClient;
import com.example.recipier.dto.SpoonacularResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpoonacularService {

    private final SpoonacularClient spoonacularClient;

    @Value("${spoonacular.api.key}")
    private String apiKey;

    public SpoonacularService(SpoonacularClient spoonacularClient) {
        this.spoonacularClient = spoonacularClient;
    }

    public SpoonacularResponse getRecipes(String query) {
        return spoonacularClient.searchRecipes(query, apiKey);
    }
}
