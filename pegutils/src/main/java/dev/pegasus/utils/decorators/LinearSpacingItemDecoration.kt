package dev.pegasus.utils.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.utils.extensions.dataTypes.toPx

/**
 * @Author: SOHAIB AHMED
 * @Date: 04,April,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class LinearSpacingItemDecoration(spacing: Int) : RecyclerView.ItemDecoration() {

    private var spacingInPixels = 0

    init {
        spacingInPixels = spacing.toPx
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = spacingInPixels
            outRect.right = spacingInPixels
        }

        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
            outRect.right = spacingInPixels
        }
    }
}