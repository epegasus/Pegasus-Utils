package dev.pegasus.utils.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import dev.pegasus.utils.utils.PegasusHelperUtils.TAG

abstract class ParentActivity<T : ViewBinding>(private val bindingFactory: (LayoutInflater) -> T, private val installSplash: Boolean = false) : AppCompatActivity() {

    protected val binding by lazy { bindingFactory(layoutInflater) }
    protected var includeTopPadding = true
    protected var includeBottomPadding = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (installSplash) {
            installSplashScreen()
        }
        enableEdgeToEdge()
        enableDynamicTheme()

        setContentView(binding.root)
        setPadding()
        hideStatusBar(-1)
        onCreated()
    }

    private fun setPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            val topPadding = if (includeTopPadding) bars.top else 0
            val bottomPadding = if (includeBottomPadding) bars.bottom else 0
            v.updatePadding(left = bars.left, top = topPadding, right = bars.right, bottom = bottomPadding)
            WindowInsetsCompat.CONSUMED
        }
    }

    protected open fun enableDynamicTheme() {}

    /**
     * @param type
     *     0: Show SystemBars
     *     1: Hide StatusBars
     *     2: Hide NavigationBars
     *     3: Hide SystemBars
     */

    protected open fun hideStatusBar(type: Int) {
        WindowInsetsControllerCompat(window, window.decorView).apply {
            systemBarsBehavior = when (type) {
                0 -> WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                else -> WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            when (type) {
                0 -> show(WindowInsetsCompat.Type.systemBars())
                1 -> hide(WindowInsetsCompat.Type.statusBars())
                2 -> hide(WindowInsetsCompat.Type.navigationBars())
                else -> hide(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

    abstract fun onCreated()
}