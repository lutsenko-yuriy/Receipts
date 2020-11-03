package com.yurich.receipts.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["recipeId", "imageUri"],
    foreignKeys = [
        ForeignKey(
            entity = DBRecipe::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DBImage::class,
            parentColumns = ["uri"],
            childColumns = ["imageUri"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeImageCrossRef(
    val recipeId: Long,
    val imageUri: String
)