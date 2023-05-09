package dev.pegasus.utils.sample.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import dev.pegasus.utils.sample.databinding.ActivityDateBinding
import dev.pegasus.utils.utils.PegasusDateUtils

const val TAG = "PegasusUtils"

class DateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.toolbarDate.setNavigationOnClickListener { finish() }
        binding.mbTimeStampDate.setOnClickListener { onTimeStampSubmit() }
        binding.mbDateGmtDate.setOnClickListener { onDateGmtSubmit() }
        binding.mbCalendarDate.setOnClickListener { onCalendarClick() }
    }

    private fun onTimeStampSubmit() {
        val timeStamp = binding.etTimeStampDate.text.toString().toLong()
        val text = "Time Elapsed: ${PegasusDateUtils.getTimePassed(timeStamp)}"
        Log.d(TAG, "updateUI: $text")
        binding.mtvTimeStampDate.text = text
    }

    private fun onDateGmtSubmit() {
        val dateGmt = binding.etDateGmtDate.text.toString()
        val text = "Time Elapsed: ${PegasusDateUtils.getTimePassed(dateGmt)}"
        Log.d(TAG, "updateUI: $text")
        binding.mtvDateGmtDate.text = text
    }

    private fun onCalendarClick() {
        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val text = "Time Elapsed: ${PegasusDateUtils.getTimePassed(it)}"
            Log.d(TAG, "updateUI: $text")
            binding.mtvCalendarDate.text = text
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }
}