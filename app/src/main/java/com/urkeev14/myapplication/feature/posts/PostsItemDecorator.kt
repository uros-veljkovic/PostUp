package com.urkeev14.myapplication.feature.posts

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PostsItemDecorator(
    private val margin: Int,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        with(outRect) {
            if (position == 0) {
                top = margin
            }
            bottom = margin
            left = margin
            right = margin
        }
    }
}
