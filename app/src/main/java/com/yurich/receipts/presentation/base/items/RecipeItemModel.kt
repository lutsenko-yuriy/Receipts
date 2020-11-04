package com.yurich.receipts.presentation.base.items

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.*
import com.yurich.receipts.R
import com.yurich.receipts.domain.ImageEntity
import com.yurich.receipts.domain.RecipeEntity
import com.yurich.receipts.presentation.base.DividerItemDecoration

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

        private lateinit var images: RecyclerView

        lateinit var rootView: View

        private val imageAdapter = Adapter()

        override fun bindView(itemView: View) {
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)

            images = itemView.findViewById<RecyclerView>(R.id.images).apply {
                layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = imageAdapter
                addItemDecoration(DividerItemDecoration(itemView.context))
            }

            rootView = itemView
        }

        fun updateImages(images: List<ImageEntity>) {
            imageAdapter.updateImages(images)
        }

        private class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

            private val items = mutableListOf<ImageEntity>()

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ViewHolder(DeletablePictureItem(parent.context))

            override fun getItemCount() = items.size

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.bind(items[position])
            }

            fun updateImages(newImages: List<ImageEntity>) {
                items.clear()
                items.addAll(newImages)
            }

            class ViewHolder(itemView: DeletablePictureItem) : RecyclerView.ViewHolder(itemView) {
                fun bind(imageEntity: ImageEntity) {
                    (itemView as? DeletablePictureItem)?.setUri(imageEntity.uri)
                }
            }
        }
    }
}