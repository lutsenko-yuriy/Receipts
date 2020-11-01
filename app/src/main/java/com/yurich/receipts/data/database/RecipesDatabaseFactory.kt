package com.yurich.receipts.data.database

import android.content.Context
import androidx.room.Room

object RecipesDatabaseFactory {

    @Volatile
    private var database: RecipesDatabase? = null

    fun getDatabase(context: Context) = database
        ?: initializeDatabase(context)

    @Synchronized
    fun initializeDatabase(context: Context) =
        database ?: run {
            database =
                Room.databaseBuilder(
                    context,
                    RecipesDatabase::class.java,
                    "recipes"
                ).build()

            database
        }

}