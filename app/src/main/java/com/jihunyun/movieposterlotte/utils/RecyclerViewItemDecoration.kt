package com.jihunyun.movieposterlotte.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION

class VerticalMarginDecoration(
    var itemMargin: Int = 0,
    var firstItemMargin: Int = 0,
    var lastItemMargin: Int = 0
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemCount = state.itemCount
        var itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == NO_POSITION) { itemPosition = parent.getChildLayoutPosition(view) }

        if (itemPosition == 0) {    // 첫 번째 아이템
            outRect.set(0, firstItemMargin, 0, (itemMargin / 2))
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {  // 마지막 아이템
            outRect.set(0, (itemMargin / 2), 0, lastItemMargin)
        } else {  // 나머지
            outRect.set(0, (itemMargin / 2), 0, (itemMargin / 2))
        }
    }
}

class GridMarginEdgeIncludeDecoration(
    var spacing :Int = 0
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        var position: Int = parent.getChildAdapterPosition(view) // item position
        if (position == RecyclerView.NO_POSITION) position = parent.getChildLayoutPosition(view)
        val spanCount = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1

        val column: Int = position % spanCount // item column

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount

        if (position < spanCount) { // top edge
            outRect.top = spacing
        }
        outRect.bottom = spacing     // item bottom
    }
}