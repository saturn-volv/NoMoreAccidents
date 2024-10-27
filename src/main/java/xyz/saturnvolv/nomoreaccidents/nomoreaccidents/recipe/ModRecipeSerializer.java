package xyz.saturnvolv.nomoreaccidents.nomoreaccidents.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;

public interface ModRecipeSerializer<T extends Recipe<?>> {
    RecipeSerializer<AxeStrippingRecipe> LOG_STRIPPING = RecipeSerializer.register("crafting_special_logstripping", new SpecialRecipeSerializer<>(AxeStrippingRecipe::new));
    static void init() {}
}
