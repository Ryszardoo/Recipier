package com.example.recipier.mapper;

import com.example.recipier.dto.RecipeDTO;
import com.example.recipier.mapper.UserMapper;
import com.example.recipier.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RecipeMapper {

    @Mapping(source = "user.username", target = "username")
    RecipeDTO toDto(Recipe recipe);

    Recipe toEntity(RecipeDTO recipeDTO);


}


