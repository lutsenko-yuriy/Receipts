package com.yurich.receipts.presentation.general

import com.airbnb.epoxy.carousel
import com.airbnb.mvrx.fragmentViewModel
import com.yurich.receipts.R
import com.yurich.receipts.presentation.base.BaseFragment
import com.yurich.receipts.presentation.base.MvRxEpoxyController
import com.yurich.receipts.presentation.base.simpleController


class GeneralFragment : BaseFragment() {

    private val viewModel: GeneralViewModel by fragmentViewModel()

    override fun titleResId() = R.string.recipes_list_title

    override fun additionalButtonImageRes() = android.R.drawable.ic_input_add

    override fun onAdditionalButtonClicked() {
        navigateTo(R.id.action_GeneralFragment_to_DetailFragment)
    }

    override fun epoxyController(): MvRxEpoxyController =
        simpleController(viewModel) { state ->
            val data = state.data() ?: emptyList()

            data.forEach { recipe ->

            }
        }

}