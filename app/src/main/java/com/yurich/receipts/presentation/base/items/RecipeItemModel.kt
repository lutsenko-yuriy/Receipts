package com.yurich.receipts.presentation.base.items

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.*
import com.yurich.receipts.R
import com.yurich.receipts.domain.ImageEntity
import com.yurich.receipts.domain.RecipeEntity
import com.yurich.receipts.presentation.base.MvRxEpoxyController

@EpoxyModelClass(layout = R.layout.recipe_item)
abstract class RecipeItemModel : EpoxyModelWithHolder<RecipeItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var recipe: RecipeEntity

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onRecipeClickListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(recipe) {
            holder.title.text = title
            holder.description.text = description

            holder.updateImages(images)

            holder.rootView.setOnClickListener(onRecipeClickListener)
        }
    }

    class Holder : EpoxyHolder() {

        lateinit var title: AppCompatTextView
        lateinit var description: AppCompatTextView

        lateinit var images: RecyclerView

        lateinit var rootView: View

        override fun bindView(itemView: View) {
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)

            images = itemView.findViewById(R.id.images)
            images.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            rootView = itemView
        }

        fun updateImages(images: List<ImageEntity>) {

        }
    }
}