package com.yurich.receipts.domain

data class RecipeEntity(
    val id: Long,
    val title: String,
    val description: String,
    val images: List<ImageEntity>
)