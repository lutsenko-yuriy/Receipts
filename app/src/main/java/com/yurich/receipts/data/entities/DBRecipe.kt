package com.yurich.receipts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DBRecipe(
    @PrimaryKey val recipeId: Long,
    val title: String,
    val description: String
)