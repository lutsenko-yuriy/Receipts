package com.yurich.receipts.presentation.general

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.yurich.receipts.data.facade.RecipesLocalStorage
import org.koin.android.ext.android.inject

class GeneralViewModel(
    initialState: GeneralViewState,
    private val storage: RecipesLocalStorage
) : BaseMvRxViewModel<GeneralViewState>(initialState, debugMode = BuildConfig.DEBUG) {

    init {
        retrieveRecipes()
    }

    fun retrieveRecipes() = withState { state ->
        if (!state.data.shouldLoad) return@withState
        storage.getAllRecipes().execute { copy(data = it) }
    }

    companion object : MvRxViewModelFactory<GeneralViewModel, GeneralViewState> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: GeneralViewState): GeneralViewModel {
            val service: RecipesLocalStorage by viewModelContext.activity.inject()
            return GeneralViewModel(state, service)
        }
    }
}