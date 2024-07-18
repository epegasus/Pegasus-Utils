package dev.pegasus.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding

abstract class ParentActivity<T : ViewBinding>(val bindingFactory: (LayoutInflater) -> T, private val installSplash: Boolean = false) : AppCompatActivity() {

    protected val binding by lazy { bindingFactory(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (installSplash) {
            installSplashScreen()
        }

        enableEdgeToEdge()
        setContentView(binding.root)
        setPadding()
        onCreated()
    }

    private fun setPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(left = bars.left, top = bars.top, right = bars.right, bottom = bars.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    abstract fun onCreated()
}