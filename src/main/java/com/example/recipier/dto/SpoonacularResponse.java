package com.example.recipier.dto;

import lombok.Data;
import java.util.List;

@Data
public class SpoonacularResponse {
    private List<Result> results;

    @Data
    public static class Result {
        private Long id;
        private String title;
        private String image;
    }
}
