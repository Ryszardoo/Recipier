package com.example.recipier.dto;

import lombok.Data;

@Data
public class RecipeDTO {
    private Long id;
    private String name;
    private String description;
    private String username;
}

