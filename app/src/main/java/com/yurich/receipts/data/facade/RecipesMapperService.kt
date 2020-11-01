package com.yurich.receipts.data.facade

import android.net.Uri
import com.yurich.receipts.data.entities.DBImage
import com.yurich.receipts.data.entities.DBRecipe
import com.yurich.receipts.data.entities.RecipeWithImages
import com.yurich.receipts.domain.ImageEntity
import com.yurich.receipts.domain.RecipeEntity

class RecipesMapperService {

    fun buildRecipeEntity(dbRecipe: RecipeWithImages) =
        RecipeEntity(
            dbRecipe.recipe.recipeId,
            dbRecipe.recipe.title,
            dbRecipe.recipe.description,
            buildImageEntities(dbRecipe.images)
        )

    private fun buildImageEntities(images: List<DBImage>) =
        images.map { buildImageEntity(it) }

    private fun buildImageEntity(dbImage: DBImage) =
        ImageEntity(
            dbImage.imageId,
            Uri.parse(dbImage.uri)
        )

    fun buildDbRecipeWithImages(recipeEntity: RecipeEntity) =
        RecipeWithImages(
            buildDbRecipe(recipeEntity),
            buildDbImages(recipeEntity)
        )

    private fun buildDbRecipe(recipeEntity: RecipeEntity) =
        DBRecipe(
            recipeEntity.id,
            recipeEntity.title,
            recipeEntity.description
        )

    private fun buildDbImages(recipeEntity: RecipeEntity) =
        recipeEntity.images.map { buildDbImage(it) }

    private fun buildDbImage(imageEntity: ImageEntity) =
        DBImage(imageEntity.id, imageEntity.uri.toString())

}