package com.yurich.receipts.presentation.base.items

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.yurich.receipts.R

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class AddNewPictureItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.add_new_picture_view, this)
            .setOnClickListener { onClicked?.invoke() }
    }

    @set:CallbackProp
    var onClicked: (() -> Unit)? = null

}

