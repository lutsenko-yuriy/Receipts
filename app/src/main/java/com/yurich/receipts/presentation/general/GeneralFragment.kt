package com.yurich.receipts.presentation.general

import com.airbnb.mvrx.fragmentViewModel
import com.yurich.receipts.R
import com.yurich.receipts.presentation.base.BaseFragment
import com.yurich.receipts.presentation.base.MvRxEpoxyController
import com.yurich.receipts.presentation.base.items.recipeItem
import com.yurich.receipts.presentation.base.simpleController
import com.yurich.receipts.presentation.detail.DetailFragmentArgument


class GeneralFragment : BaseFragment() {

    private val viewModel: GeneralViewModel by fragmentViewModel()

    override fun titleResId() = R.string.recipes_list_title

    override fun additionalButtonImageRes() = android.R.drawable.ic_input_add

    override fun onResume() {
        super.onResume()
        viewModel.retrieveRecipes()
    }

    override fun onAdditionalButtonClicked() {
        navigateTo(
            R.id.action_GeneralFragment_to_DetailFragment,
            DetailFragmentArgument()
        )
    }

    override fun epoxyController(): MvRxEpoxyController =
        simpleController(viewModel) { state ->
            val data = state.data() ?: emptyList()

            data.forEach { recipe ->
                recipeItem {
                    id(recipe.id)
                    recipe(recipe)
                    onRecipeClickListener { _, _, _, _ ->
                        navigateTo(
                            R.id.action_GeneralFragment_to_DetailFragment,
                            DetailFragmentArgument(recipe.id)
                        )
                    }
                }
            }
        }

}