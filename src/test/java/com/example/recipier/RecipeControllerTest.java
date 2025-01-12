/*
package com.example.recipier;

import com.example.recipier.controller.RecipeController;
import com.example.recipier.dto.RecipeDTO;
import com.example.recipier.model.Category;
import com.example.recipier.model.Recipe;
import com.example.recipier.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Main Course");

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pasta");
        recipe.setDescription("Delicious pasta recipe");
        recipe.setCategory(category);

        Mockito.when(recipeService.getAllRecipes()).thenReturn(Collections.singletonList(recipe));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void shouldGetAllRecipes() throws Exception {
        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pasta"))
                .andExpect(jsonPath("$[0].description").value("Delicious pasta recipe"))
                .andExpect(jsonPath("$[0].categoryName").value("Main Course"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void shouldAddRecipeSuccessfully() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO(recipe);

        Mockito.when(recipeService.addRecipe(Mockito.any(RecipeDTO.class), Mockito.any()))
                .thenReturn(recipe);

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Pasta\", \"description\": \"Delicious pasta recipe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(recipeDTO.getName()))
                .andExpect(jsonPath("$.description").value(recipeDTO.getDescription()));
    }
}
*/
