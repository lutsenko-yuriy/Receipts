package com.yurich.receipts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yurich.receipts.data.dao.RecipesDao
import com.yurich.receipts.data.entities.DBImage
import com.yurich.receipts.data.entities.DBRecipe

@Database(entities = [DBRecipe::class, DBImage::class], version = 1)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}