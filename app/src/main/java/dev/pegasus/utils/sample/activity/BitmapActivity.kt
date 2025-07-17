package dev.pegasus.utils.sample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.pegasus.utils.sample.databinding.ActivityBitmapBinding

class BitmapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBitmapBinding.inflate(layoutInflater) }

    /*private var job: Job? = null
    private var filePath: String? = null

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val filePath = PegasusFileUtils.getFilePathFromUri(this, it, 1)
            this.filePath = filePath
            setImageFilePath(filePath)
        }
    }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //setImageDrawable()
        //setImageResource()
        //setImageFilePath()
    }
}

/**
 * Note: This method also takes time to execute, you should call this method from the background thread.
 */

/*private fun setImageDrawable() {
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
}*/