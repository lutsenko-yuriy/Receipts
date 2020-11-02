package com.yurich.receipts.presentation.base.items

import android.content.Context
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ScrollView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.yurich.receipts.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class RecipeDetailItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private val titleView by lazy { findViewById<EditText>(R.id.recipe_title) }
    private val descriptionView by lazy { findViewById<EditText>(R.id.recipe_description) }

    private val titleWatcher = SimpleTextWatcher { onTitleChanged?.invoke(it) }
    private val descriptionWatcher = SimpleTextWatcher { onDescriptionChanged?.invoke(it) }

    init {
        inflate(context, R.layout.add_edit_recipe_view, this)
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
}

fun EditText.setTextIfDifferent(newText: CharSequence?): Boolean {
    if (!isTextDifferent(newText, text)) {
        return false
    }

    setText(newText)
    setSelection(newText?.length ?: 0)
    return true
}

fun isTextDifferent(str1: CharSequence?, str2: CharSequence?): Boolean {
    if (str1 === str2) {
        return false
    }
    if (str1 == null || str2 == null) {
        return true
    }
    val length = str1.length
    if (length != str2.length) {
        return true
    }

    if (str1 is Spanned) {
        return str1 != str2
    }

    for (i in 0 until length) {
        if (str1[i] != str2[i]) {
            return true
        }
    }
    return false
}

private class SimpleTextWatcher(val onTextChanged: (newText: String) -> Unit) : TextWatcher {
    override fun afterTextChanged(s: Editable) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onTextChanged(s.toString())
    }
}