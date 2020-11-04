package com.yurich.receipts.presentation.base.items

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
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
    var onRecipeClickListener: (() -> Unit)? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(recipe) {
            holder.title.text = title
            holder.description.text = description

            holder.updateImages(images)

            holder.rootView.setOnClickListener { onRecipeClickListener?.invoke() }
            holder.imageAdapter.setOnClickListener(onRecipeClickListener)
        }
    }

    class Holder : EpoxyHolder() {

        lateinit var title: AppCompatTextView
        lateinit var description: AppCompatTextView

        lateinit var images: RecyclerView

        lateinit var rootView: View

        val imageAdapter = Adapter()

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
            if (images.isNotEmpty()) {
                imageAdapter.updateImages(images)
                this.images.visibility = View.VISIBLE
            } else {
                this.images.visibility = View.GONE
            }
        }

        class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

            private val items = mutableListOf<ImageEntity>()

            private var imageClickListener: (() -> Unit)? = null

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val viewHolder = ViewHolder(DeletablePictureItem(parent.context))
                viewHolder.itemView.setOnClickListener {
                    if (viewHolder.adapterPosition != NO_POSITION) {
                        imageClickListener?.invoke()
                    }
                }
                return viewHolder
            }

            override fun getItemCount() = items.size

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.bind(items[position])
            }

            fun updateImages(newImages: List<ImageEntity>) {
                items.clear()
                items.addAll(newImages)

                notifyDataSetChanged()
            }

            fun setOnClickListener(listener: (() -> Unit)?) {
                imageClickListener = listener
            }

            class ViewHolder(itemView: DeletablePictureItem) : RecyclerView.ViewHolder(itemView) {
                fun bind(imageEntity: ImageEntity) {
                    (itemView as? DeletablePictureItem)?.setUri(imageEntity.uri)
                }
            }
        }
    }
}