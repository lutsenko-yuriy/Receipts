package com.yurich.receipts.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RecipeWithImages(
    @Embedded val recipe: DBRecipe,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "imageId",
        associateBy = Junction(RecipeWithImages::class)
    )
    val images: List<DBImage>
)