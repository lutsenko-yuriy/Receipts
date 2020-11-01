package com.yurich.receipts.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["recipeId", "imageId"])
data class RecipeImageCrossRef(
    val recipeId: Long,
    val imageId: Long
)