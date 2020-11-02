package com.yurich.receipts.presentation.detail

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.yurich.receipts.domain.RecipeEntity

data class DetailViewState(
    val id: Long?,
    val currentData: Async<RecipeEntity> = Uninitialized
) : MvRxState {

    constructor(argument: DetailFragmentArgument): this(id = argument.id)

}