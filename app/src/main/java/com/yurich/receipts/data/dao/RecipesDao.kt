package com.yurich.receipts.data.dao

import androidx.room.*
import com.yurich.receipts.data.entities.DBRecipe
import com.yurich.receipts.data.entities.RecipeWithImages
import io.reactivex.Single

@Dao
interface RecipesDao {

    @Transaction
    @Query("SELECT * FROM dbrecipe")
    fun getAll(): Single<List<RecipeWithImages>>

    @Transaction
    @Query("SELECT * FROM dbrecipe WHERE recipeId IN (:ids)")
    fun getAllByIds(ids: List<Long>): Single<List<RecipeWithImages>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<RecipeWithImages>): Single<List<Long>>

}