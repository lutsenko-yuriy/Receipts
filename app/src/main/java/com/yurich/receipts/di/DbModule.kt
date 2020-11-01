package com.yurich.receipts.di

import com.yurich.receipts.data.database.RecipesDatabase
import com.yurich.receipts.data.database.RecipesDatabaseFactory
import com.yurich.receipts.data.facade.RecipesDaoDelegate
import com.yurich.receipts.data.facade.RecipesLocalStorage
import com.yurich.receipts.data.facade.RecipesMapperService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single { RecipesDatabaseFactory.getDatabase(androidContext()) }

    single { get<RecipesDatabase>().recipesDao() }

    single { RecipesMapperService() }

    single<RecipesLocalStorage> { RecipesDaoDelegate(get(), get()) }

}