package dev.pegasus.utils.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import dev.pegasus.utils.base.ParentActivity
import dev.pegasus.utils.sample.activity.BitmapActivity
import dev.pegasus.utils.sample.activity.DateActivity
import dev.pegasus.utils.sample.activity.FileActivity
import dev.pegasus.utils.sample.databinding.ActivityMainBinding

class MainActivity : ParentActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ASD", "MainActivity: onCreate: called")
        super.onCreate(savedInstanceState)
        Log.d("ASD", "MainActivity: onCreate: removed")
    }

    override fun onCreated() {
        binding.mbBitmapMain.setOnClickListener { startActivity(Intent(this, BitmapActivity::class.java)) }
        binding.mbDateMain.setOnClickListener { startActivity(Intent(this, DateActivity::class.java)) }
        binding.mbFileMain.setOnClickListener { startActivity(Intent(this, FileActivity::class.java)) }
    }
}