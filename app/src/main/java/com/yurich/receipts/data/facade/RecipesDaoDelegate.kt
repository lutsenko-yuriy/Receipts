package com.yurich.receipts.data.facade

import com.yurich.receipts.data.dao.RecipesDao
import com.yurich.receipts.domain.RecipeEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RecipesDaoDelegate(
    private val dao: RecipesDao,
    private val mapper: RecipesMapperService
): RecipesLocalStorage {

    override fun getAllRecipes() =
        dao.getAll()
            .subscribeOn(Schedulers.io())
            .map { recipes ->
                recipes.map { mapper.buildRecipeEntity(it) }
            }

    override fun getRecipeByIds(ids: List<Long>) =
        dao.getAllByIds(ids)
            .subscribeOn(Schedulers.io())
            .map { recipes ->
                recipes.map { mapper.buildRecipeEntity(it) }
            }

    override fun updateRecipes(recipes: List<RecipeEntity>) =
        Single.merge(listOf(
            dao.insertRecipes(recipes.map { mapper.buildDbRecipe(it) }),
            dao.insertImages(recipes.map { mapper.buildDbImages(it) }.flatten()),
            dao.insertRelations(recipes.map { mapper.buildDbRelation(it) }.flatten())
        )).lastOrError().map { recipes }

}