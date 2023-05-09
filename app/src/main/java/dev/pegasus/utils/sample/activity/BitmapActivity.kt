package dev.pegasus.utils.sample.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.pegasus.utils.sample.R
import dev.pegasus.utils.sample.databinding.ActivityBitmapBinding
import dev.pegasus.utils.utils.PegasusBitmapUtils
import dev.pegasus.utils.utils.PegasusFileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BitmapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBitmapBinding.inflate(layoutInflater) }
    private var job: Job? = null
    private var filePath: String? = null

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val filePath = PegasusFileUtils.getFilePathFromUri(this, it, 1)
            this.filePath = filePath
            setImageFilePath(filePath)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //setImageDrawable()
        //setImageResource()
        //setImageFilePath()
    }

    /**
     * Note: This method also takes time to execute, you should call this method from the background thread.
     */

    private fun setImageDrawable() {
        val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.img_medium)
        drawable?.let {
            job = PegasusBitmapUtils.getBitmapFromImageDrawable(it, true, object : PegasusBitmapUtils.BitmapCallback {
                override fun onProcessing() {
                    binding.progressBarBitmap.visibility = View.VISIBLE
                }

                override fun onSuccess(bitmap: Bitmap) {
                    binding.progressBarBitmap.visibility = View.GONE
                    binding.sivImageBitmap.setImageBitmap(bitmap)
                }

                override fun onFailure(errorMessage: String) {
                    binding.progressBarBitmap.visibility = View.GONE
                    Toast.makeText(this@BitmapActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setImageResource() {
        job = PegasusBitmapUtils.getBitmapFromImageResource(this, R.drawable.img_medium, false, object : PegasusBitmapUtils.BitmapCallback {
            override fun onProcessing() {
                binding.progressBarBitmap.visibility = View.VISIBLE
            }

            override fun onSuccess(bitmap: Bitmap) {
                binding.progressBarBitmap.visibility = View.GONE
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        binding.sivImageBitmap.setImageBitmap(bitmap)
                    } catch (ex: RuntimeException) {
                        Log.d("TAG", "onSuccess: $ex")
                    }
                }
            }

            override fun onFailure(errorMessage: String) {
                binding.progressBarBitmap.visibility = View.GONE
                Toast.makeText(this@BitmapActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setImageFilePath() {
        resultLauncher.launch("image/*")
    }

    private fun setImageFilePath(filePath: String) {
        job = PegasusBitmapUtils.getBitmapFromFilePath(filePath, true, object : PegasusBitmapUtils.BitmapCallback {
            override fun onProcessing() {
                binding.progressBarBitmap.visibility = View.VISIBLE
            }

            override fun onSuccess(bitmap: Bitmap) {
                binding.progressBarBitmap.visibility = View.GONE
                binding.sivImageBitmap.setImageBitmap(bitmap)
            }

            override fun onFailure(errorMessage: String) {
                binding.progressBarBitmap.visibility = View.GONE
                Toast.makeText(this@BitmapActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        job = null
    }
}