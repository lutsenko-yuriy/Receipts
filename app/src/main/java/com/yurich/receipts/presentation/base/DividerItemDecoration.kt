package com.yurich.receipts.presentation.base

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

private const val PADDING_IN_DIPS = 16

class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    
    private val padding = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, PADDING_IN_DIPS.toFloat(), context.resources.displayMetrics
    ).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }
        outRect.left = padding
        val adapter = parent.adapter
        if (adapter != null && itemPosition == adapter.itemCount - 1) {
            outRect.right = padding
        }
    }
    
}