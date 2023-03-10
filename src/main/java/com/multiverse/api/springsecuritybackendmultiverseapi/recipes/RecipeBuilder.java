package com.multiverse.api.springsecuritybackendmultiverseapi.recipes;

import com.multiverse.api.springsecuritybackendmultiverseapi.auth.Extract;
import com.multiverse.api.springsecuritybackendmultiverseapi.user.User;
import com.multiverse.api.springsecuritybackendmultiverseapi.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeBuilder {
    private final Extract extract;
    private final UserRepository userRepository;

    public Recipe build(HttpServletRequest token, RecipeRequest recipeRequest){
        Recipe recipeBuild = Recipe.builder()
                .title(recipeRequest.getTitle())
                .category(recipeRequest.getCategory())
                .createBy(extract.emailFromJwt(token))
                .build();
        User user = userRepository.findByEmail(extract.emailFromJwt(token)).orElseGet(User::new);
        user.getRecipes().add(recipeBuild);
        return recipeBuild;
    }
}
