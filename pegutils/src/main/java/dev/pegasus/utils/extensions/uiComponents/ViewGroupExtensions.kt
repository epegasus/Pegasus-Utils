package dev.pegasus.utils.extensions.uiComponents

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

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