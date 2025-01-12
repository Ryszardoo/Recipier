package com.example.recipier.mapper;

import com.example.recipier.dto.UserDTO;
import com.example.recipier.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RecipeMapper.class)
public interface UserMapper {
    UserDTO toDto(User user);
    @Mapping(target = "recipes", expression = "java(userDTO.getRecipes() != null ? recipeMapper.toEntityList(userDTO.getRecipes()) : null)")
    User toEntity(UserDTO userDTO);
}
