package com.example.recipier.APIs;

import com.example.recipier.dto.SpoonacularResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spoonacular", url = "https://api.spoonacular.com")
public interface SpoonacularClient {

    @GetMapping("/recipes/complexSearch")
    SpoonacularResponse searchRecipes(
            @RequestParam("query") String query,
            @RequestParam("apiKey") String apiKey
    );
}
