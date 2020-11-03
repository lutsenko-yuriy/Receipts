package com.yurich.receipts.presentation.detail

import android.app.Activity.RESULT_OK
import android.content.Intent
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.carousel
import com.airbnb.mvrx.fragmentViewModel
import com.yurich.receipts.R
import com.yurich.receipts.domain.ImageEntity
import com.yurich.receipts.presentation.base.BaseFragment
import com.yurich.receipts.presentation.base.items.AddNewPictureItemModel_
import com.yurich.receipts.presentation.base.items.DeletablePictureItemModel_
import com.yurich.receipts.presentation.base.items.recipeDetailItemView
import com.yurich.receipts.presentation.base.simpleController


class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by fragmentViewModel()

    override fun titleResId() = R.string.recipe_detail_title

    override fun additionalButtonImageRes() = android.R.drawable.ic_menu_save

    override fun onAdditionalButtonClicked() {
        viewModel.save()
        activity?.onBackPressed()
    }

    override fun epoxyController() =
        simpleController(viewModel) { state ->
            val currentData = state.currentData() ?: return@simpleController

            recipeDetailItemView {
                id("Text fields")

                title(currentData.title)
                onTitleChanged { viewModel.onTitleUpdated(it) }

                description(currentData.description)
                onDescriptionChanged { viewModel.onDescriptionUpdated(it) }
            }

            carousel {
                id("carousel")

                val models = mutableListOf<EpoxyModel<*>>(
                    AddNewPictureItemModel_()
                        .id("Add new picture")
                        .onClicked { requestLoadingNewPicture() }
                )
                models.addAll(
                    currentData.images.map {
                        DeletablePictureItemModel_()
                            .id(it.uri.toString())
                            .uri(it.uri)
                            .onRemoveRequested { onRemoveImageRequested(it) }
                    }
                )
                models(models)
            }
        }

    private fun requestLoadingNewPicture() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_OPEN_DOCUMENT
        }
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            SELECT_PICTURE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data ?: return
            viewModel.addNewImage(selectedImageUri)
        }
    }

    private fun onRemoveImageRequested(imageEntity: ImageEntity) {
        viewModel.removeImage(imageEntity)
    }

    companion object {
        const val SELECT_PICTURE = 1
    }

}