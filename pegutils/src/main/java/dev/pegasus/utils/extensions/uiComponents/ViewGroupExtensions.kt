package dev.pegasus.utils.extensions.uiComponents

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

fun ViewGroup.addCleanView(view: View) {
    (view.parent as? ViewGroup)?.removeView(view)
    this.removeAllViews()
    this.addView(view)
}

/**
 *  -> e.g. viewPager2.addCarouselEffect()
 * @param  enableZoom: Zooming effect for center card/view
 */

fun ViewPager2.addCarouselEffect(enableZoom: Boolean = true) {
    clipChildren = false    // No clipping the left and right items
    clipToPadding = false   // Show the viewpager in full width without clipping the padding
    offscreenPageLimit = 3  // Render the left and right items
    (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect

    val compositePageTransformer = CompositePageTransformer()
    compositePageTransformer.addTransformer(MarginPageTransformer((20 * Resources.getSystem().displayMetrics.density).toInt()))
    if (enableZoom) {
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
    }
    setPageTransformer(compositePageTransformer)
}

/**
 *  -> e.g. viewPager2.startAutoScroll(1000)
 * @param  intervalMillis: Time in milliseconds to change the slide
 * @param autoScrollHandler: Pass your own handler to handle scroll
 *
 * Note: passing same handler will be able to remove scroll
 */

fun ViewPager2.startAutoScroll(autoScrollHandler: Handler, intervalMillis: Long = 3000) {
    val autoScrollRunnable = object : Runnable {
        override fun run() {
            val currentItem = currentItem
            val nextItem = if (currentItem == adapter?.itemCount?.minus(1)) 0 else currentItem + 1
            this@startAutoScroll.currentItem = nextItem
            autoScrollHandler.postDelayed(this, intervalMillis)
        }
    }
    autoScrollHandler.postDelayed(autoScrollRunnable, intervalMillis)
}

fun ViewPager2.stopAutoScroll(handler: Handler) {
    handler.removeCallbacksAndMessages(null)
}