package com.yurich.receipts.presentation.general

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.yurich.receipts.data.facade.RecipesLocalStorage

class GeneralViewModel(
    initialState: GeneralViewState
    private val storage: RecipesLocalStorage
) : BaseMvRxViewModel<GeneralViewState>(initialState, debugMode = BuildConfig.DEBUG) {

    fun retrieveRecipes() = withState { state ->
        if (!state.data.shouldLoad) return@withState
        storage.getAllRecipes().execute { copy(data = it) }
    }
}