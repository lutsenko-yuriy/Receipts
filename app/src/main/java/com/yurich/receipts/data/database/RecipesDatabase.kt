package com.yurich.receipts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yurich.receipts.data.dao.RecipesDao
import com.yurich.receipts.data.entities.DBImage
import com.yurich.receipts.data.entities.DBRecipe
import com.yurich.receipts.data.entities.RecipeImageCrossRef

@Database(entities = [DBRecipe::class, DBImage::class, RecipeImageCrossRef::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}