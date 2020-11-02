package com.yurich.receipts.data.dao

import androidx.room.*
import com.yurich.receipts.data.entities.DBImage
import com.yurich.receipts.data.entities.DBRecipe
import com.yurich.receipts.data.entities.RecipeImageCrossRef
import com.yurich.receipts.data.entities.RecipeWithImages
import io.reactivex.Single

@Dao
abstract class RecipesDao {

    @Transaction
    @Query("SELECT * FROM dbrecipe")
    abstract fun getAll(): Single<List<RecipeWithImages>>

    @Transaction
    @Query("SELECT * FROM dbrecipe WHERE recipeId IN (:ids)")
    abstract fun getAllByIds(ids: List<Long>): Single<List<RecipeWithImages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRecipes(recipes: List<DBRecipe>): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImages(images: List<DBImage>): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertRelations(crossRefs: List<RecipeImageCrossRef>): Single<List<Long>>

    @Query("DELETE FROM RecipeImageCrossRef WHERE recipeId IN (:recipeIds)")
    abstract fun removeRelationsOfRecipe(recipeIds: List<Long>): Int

}