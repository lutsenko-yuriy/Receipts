package com.yurich.receipts.presentation.base.items

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import com.airbnb.epoxy.*
import com.yurich.receipts.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class RecipeDetailItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView by lazy { findViewById<EditText>(R.id.recipe_title) }
    private val descriptionView by lazy { findViewById<EditText>(R.id.recipe_description) }

    private val titleWatcher = simpleTextWatcher { onTitleChanged?.invoke(it) }
    private val descriptionWatcher = simpleTextWatcher { onDescriptionChanged?.invoke(it) }

    init {
        inflate(context, R.layout.add_edit_recipe_view, this)

        val padding = (context.resources.displayMetrics.density * 16).toInt()

        setPadding(padding, padding, padding, padding)
        orientation = VERTICAL

        titleView.addTextChangedListener(titleWatcher)
        descriptionView.addTextChangedListener(descriptionWatcher)
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        titleView.setTextIfDifferent(title)
    }

    @TextProp
    fun setDescription(description: CharSequence?) {
        descriptionView.setTextIfDifferent(description)
    }

    @set:CallbackProp
    var onTitleChanged: ((newText: String) -> Unit)? = null

    @set:CallbackProp
    var onDescriptionChanged: ((newText: String) -> Unit)? = null

    fun simpleTextWatcher(onTextChanged: (newText: String) -> Unit) = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }
    }

    private fun EditText.setTextIfDifferent(newText: CharSequence?): Boolean {
        if (newText.toString() != text.toString()) {
            return false
        }

        setText(newText)
        setSelection(newText?.length ?: 0)
        return true
    }
}