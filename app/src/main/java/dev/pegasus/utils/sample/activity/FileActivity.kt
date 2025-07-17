package dev.pegasus.utils.sample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.pegasus.utils.sample.databinding.ActivityFileBinding
import dev.pegasus.utils.utils.PegasusFileUtils

class FileActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getExternalStorageSupport()
    }

    private fun getExternalStorageSupport() {
        val storageState = PegasusFileUtils.isExternalStorageWritable()
        binding.tvExternalAvailable.text = storageState.message
    }
}