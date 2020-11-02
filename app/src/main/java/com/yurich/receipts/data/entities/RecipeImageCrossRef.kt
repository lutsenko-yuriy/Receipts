package com.yurich.receipts.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["recipeId", "imageId"],
    foreignKeys = [
        ForeignKey(
            entity = DBRecipe::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DBImage::class,
            parentColumns = ["imageId"],
            childColumns = ["imageId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeImageCrossRef(
    val recipeId: Long,
    val imageId: Long
)