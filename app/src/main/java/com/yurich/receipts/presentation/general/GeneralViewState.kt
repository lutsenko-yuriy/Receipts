package com.yurich.receipts.presentation.general

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.yurich.receipts.domain.RecipeEntity

data class GeneralViewState(
    val data: Async<List<RecipeEntity>> = Uninitialized
) : MvRxState