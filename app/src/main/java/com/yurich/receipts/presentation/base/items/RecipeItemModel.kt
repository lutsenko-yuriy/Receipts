package com.yurich.receipts.presentation.base.items

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.yurich.receipts.R
import com.yurich.receipts.domain.RecipeEntity

@EpoxyModelClass(layout = R.layout.recipe_item)
abstract class RecipeItemModel : EpoxyModelWithHolder<RecipeItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var recipe: RecipeEntity

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(recipe) {
            holder.title.text = title
            holder.description.text = description
        }
    }

    class Holder : EpoxyHolder() {

        lateinit var title: AppCompatTextView
        lateinit var description: AppCompatTextView

        override fun bindView(itemView: View) {
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)
        }
    }
}