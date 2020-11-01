package com.yurich.receipts.data.facade

import com.yurich.receipts.domain.RecipeEntity
import io.reactivex.Single

interface RecipesLocalStorage {
    fun getAllRecipes(): Single<List<RecipeEntity>>
    fun getRecipeByIds(ids: List<Long>): Single<List<RecipeEntity>>
    fun updateRecipes(recipes: List<RecipeEntity>): Single<List<RecipeEntity>>
}
