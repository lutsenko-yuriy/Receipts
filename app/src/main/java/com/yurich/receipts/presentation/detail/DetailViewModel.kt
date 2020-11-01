package com.yurich.receipts.presentation.detail

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.yurich.receipts.data.facade.RecipesLocalStorage
import org.koin.android.ext.android.inject

class DetailViewModel(
    initialState: DetailViewState,
    private val storage: RecipesLocalStorage
) : BaseMvRxViewModel<DetailViewState>(initialState, debugMode = BuildConfig.DEBUG) {

    init {
        retrieveRecipes()
    }

    fun retrieveRecipes() = withState { state ->
        if (!state.data.shouldLoad) return@withState
        storage.getAllRecipes().execute { copy(data = it) }
    }

    companion object : MvRxViewModelFactory<DetailViewModel, DetailViewState> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: DetailViewState): DetailViewModel {
            val service: RecipesLocalStorage by viewModelContext.activity.inject()
            return DetailViewModel(state, service)
        }
    }
}