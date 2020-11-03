package com.yurich.receipts.presentation.detail

import android.net.Uri
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.yurich.receipts.data.facade.RecipesLocalStorage
import com.yurich.receipts.domain.ImageEntity
import com.yurich.receipts.domain.RecipeEntity
import io.reactivex.Single
import org.koin.android.ext.android.inject
import java.util.*

class DetailViewModel(
        initialState: DetailViewState,
        private val storage: RecipesLocalStorage
) : BaseMvRxViewModel<DetailViewState>(initialState, debugMode = BuildConfig.DEBUG) {

    init {
        retrieveInitialRecipe()
    }

    fun retrieveInitialRecipe() = withState { state ->
        if (!state.currentData.shouldLoad) return@withState
        if (state.id == null) {
            Single.just(buildNewRecipe())
        } else {
            storage.getRecipeByIds(listOf(state.id))
                    .map { it.firstOrNull() ?: buildNewRecipe(state.id) }
        }.execute { copy(currentData = it) }
    }

    fun onTitleUpdated(title: String) = withState {
        val copy = it.currentData()?.copy(title = title) ?: return@withState
        Single.just(copy)
                .execute { newData -> copy(currentData = newData) }
    }

    fun onDescriptionUpdated(description: String) = withState {
        val copy = it.currentData()?.copy(description = description) ?: return@withState
        Single.just(copy)
                .execute { newData -> copy(currentData = newData) }
    }

    fun save() = withState {
        val currentData = it.currentData() ?: return@withState

        storage.updateRecipes(listOf(currentData))
                .map { recipe -> recipe.first() }
                .execute { data -> copy(currentData = data) }
    }

    private fun buildNewRecipe(id: Long = Calendar.getInstance().timeInMillis) =
            RecipeEntity(id = id)

    fun addNewImage(selectedImageUri: Uri) = withState {
        val oldState = it.currentData() ?: return@withState
        val newState = oldState.copy(images = oldState.images + buildNewImage(selectedImageUri = selectedImageUri))
        Single.just(newState)
                .execute { newData -> copy(currentData = newData) }
    }

    private fun buildNewImage(
            selectedImageUri: Uri
    ) = ImageEntity(selectedImageUri)

    companion object : MvRxViewModelFactory<DetailViewModel, DetailViewState> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: DetailViewState): DetailViewModel {
            val service: RecipesLocalStorage by viewModelContext.activity.inject()
            return DetailViewModel(state, service)
        }
    }
}