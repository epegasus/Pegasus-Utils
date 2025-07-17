package dev.pegasus.utils.extensions.components

import android.content.res.Resources
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * @Author: SOHAIB AHMED
 * @Date: 26,March,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

/**
 * @param type: e.g. "1:1", "2:3", "3:4", "16:9", etc
 * @param clContainer: Parent Layout as a container
 */

fun ViewGroup.applyAspectRatio(type: String, clContainer: ConstraintLayout) {
    val set = ConstraintSet()
    set.clone(clContainer)
    set.setDimensionRatio(this.id, type)
    set.applyTo(clContainer)
}

/**
 *  -> e.g. frameLayout.addCleanView(adView)
 * @param  view: Here AdView is Child
 */

fun ViewGroup?.addCleanView(view: View?) {
    this ?: return
    view ?: return
    (view.parent as? ViewGroup)?.removeView(view)
    this.removeAllViews()
    this.addView(view)
}