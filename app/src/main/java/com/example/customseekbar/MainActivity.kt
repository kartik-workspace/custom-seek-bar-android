package com.example.customseekbar

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.customseekbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var seekBar: SeekBar
    private lateinit var labels: Array<TextView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seekBar = findViewById(R.id.seekBar)
        labels = arrayOf(
            findViewById(R.id.label0),
            findViewById(R.id.label5),
            findViewById(R.id.label10),
            findViewById(R.id.label50),
            findViewById(R.id.label100)
        )

        // Set the maximum count of SeekBar based on the labels
        seekBar.max = labels.last().text.toString().toInt()

        // Set thumb position according to user count (0 to max)
        val userCount = 75 // Example user count
        seekBar.progress = userCount
        setThumbPosition(userCount)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateLabelsColor(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not needed
            }
        })

        updateLabelsColor(userCount)
    }

    private fun updateLabelsColor(progress: Int) {
        for (i in 0 until labels.size) {
            if (labels[i].text.toString().toInt() <= progress) {
                labels[i].setTextColor(getColor(R.color.black))
            } else {
                labels[i].setTextColor(getColor(R.color.grey))
            }
        }
    }

    private fun setThumbPosition(progress: Int) {
        var minDiff = Int.MAX_VALUE
        var closestIndex = 0

        for (i in 0 until labels.size) {
            val diff = Math.abs(labels[i].text.toString().toInt() - progress)
            if (diff < minDiff) {
                minDiff = diff
                closestIndex = i
            }
        }

        val thumbX = labels[closestIndex].x + labels[closestIndex].width / 2
        seekBar.thumb.setBounds(thumbX.toInt(), 0, thumbX.toInt() + seekBar.thumb.intrinsicWidth, seekBar.thumb.intrinsicHeight)
        seekBar.thumbOffset = 0
    }
}