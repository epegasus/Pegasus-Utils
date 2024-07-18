package dev.pegasus.utils.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.pegasus.utils.sample.activity.BitmapActivity
import dev.pegasus.utils.sample.activity.DateActivity
import dev.pegasus.utils.sample.activity.FileActivity
import dev.pegasus.utils.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mbBitmapMain.setOnClickListener { startActivity(Intent(this, BitmapActivity::class.java)) }
        binding.mbDateMain.setOnClickListener { startActivity(Intent(this, DateActivity::class.java)) }
        binding.mbFileMain.setOnClickListener { startActivity(Intent(this, FileActivity::class.java)) }
    }
}