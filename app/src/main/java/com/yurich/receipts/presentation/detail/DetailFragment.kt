package com.yurich.receipts.presentation.detail

import com.airbnb.mvrx.fragmentViewModel
import com.yurich.receipts.R
import com.yurich.receipts.presentation.base.BaseFragment
import com.yurich.receipts.presentation.base.items.recipeDetailItemView
import com.yurich.receipts.presentation.base.simpleController

class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by fragmentViewModel()

    override fun titleResId() = R.string.recipe_detail_title

    override fun additionalButtonImageRes() = android.R.drawable.ic_menu_save

    override fun onAdditionalButtonClicked() {
        viewModel.save()
    }

    override fun epoxyController() =
        simpleController(viewModel) { state ->
            recipeDetailItemView {
                val currentData = state.currentData()
                id(state.id)
                title(currentData?.title)
                description(currentData?.description)
                onTitleChanged { viewModel.onTitleUpdated(it) }
                onDescriptionChanged { viewModel.onDescriptionUpdated(it) }
            }
        }


}