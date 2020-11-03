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
            .map { recipes ->
                recipes.map { mapper.buildRecipeEntity(it) }
            }

    override fun getRecipeByIds(ids: List<Long>) =
        dao.getAllByIds(ids)
            .map { recipes ->
                recipes.map { mapper.buildRecipeEntity(it) }
            }

    override fun updateRecipes(recipes: List<RecipeEntity>) =
        Single.just(dao.removeRelationsOfRecipe(recipes.map { it.id }))
            .flatMap { dao.insertRecipes(recipes.map { mapper.buildDbRecipe(it) }) }
            .flatMap {
                dao.insertImages(recipes.map { mapper.buildDbImages(it) }.flatten())
            }
            .flatMap {
                dao.insertRelations(recipes.map { mapper.buildDbRelation(it) }.flatten())
            }
            .flatMap { dao.getAllByIds(it) }
            .map { savedRecipes ->
                savedRecipes.map { recipe ->
                    mapper.buildRecipeEntity(recipe)
                }
            }

}