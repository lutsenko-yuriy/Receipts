package com.yurich.receipts.presentation.base.items

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.yurich.receipts.R

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class DeletablePictureItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val image by lazy { findViewById<AppCompatImageView>(R.id.picture) }
    private val imageRemove by lazy { findViewById<AppCompatImageView>(R.id.remove) }

    init {
        inflate(context, R.layout.deletable_picture_view, this)
        imageRemove.setOnClickListener { onRemoveRequested?.invoke() }
    }

    @ModelProp
    fun setUri(uri: Uri) {
        val imageStream = context.contentResolver.openInputStream(uri)
        image.setImageBitmap(BitmapFactory.decodeStream(imageStream))
    }

    @set:CallbackProp
    var onRemoveRequested: (() -> Unit)? = null

}