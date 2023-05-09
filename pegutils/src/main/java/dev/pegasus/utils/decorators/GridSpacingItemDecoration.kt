package dev.pegasus.utils.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.utils.extensions.tools.px

/**
 * @Author: SOHAIB AHMED
 * @Date: 04,April,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class GridSpacingItemDecoration(spacing: Int, private val spanCount: Int) : RecyclerView.ItemDecoration() {

    private var spacingInPixels = 0

    init {
        spacingInPixels = spacing.px
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (spanCount == 2) {
            outRect.left = spacingInPixels - column * spacingInPixels / spanCount
            outRect.right = (column + 1) * spacingInPixels / spanCount
        } else {
            outRect.left = spacingInPixels - column * spacingInPixels / spanCount
            // 2 - 0 * 2 / 3 = 2
            // 2 - 1 * 2 / 3 = 1.33
            // 2 - 2 * 2 / 3 = 0.66

            outRect.right = column * spacingInPixels / spanCount
            // 0 * 2 / 3 = 0
            // 1 * 2 / 3 = 0.66
            // 2 * 2 / 3 = 1.33
        }

        if (position < spanCount) {
            outRect.top = spacingInPixels
        }
        outRect.bottom = spacingInPixels
    }
}