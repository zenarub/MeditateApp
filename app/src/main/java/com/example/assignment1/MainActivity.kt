package com.example.assignment1

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val startBt : Button = findViewById(R.id.startBt)
    private val pauseBt : Button = findViewById(R.id.pauseBt)
    private val resetBt : Button = findViewById(R.id.resetBt)
    private var timerTextView: TextView = findViewById(R.id.timerTextView)
    var timerDuration : Long = 0
    var isTimerRunning = false
    private lateinit var countDownTimer: CountDownTimer
    private val spinX : Spinner = findViewById(R.id.spinnerTime)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val choices = arrayOf("5 minutes","10 minutes","15 minutes")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, choices)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinX.adapter = adapter

        spinX.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (choices[p2]) {
                    "5 minutes" -> timerDuration = 5 * 1000 * 60
                    "10 minutes" -> timerDuration = 10 * 1000 * 60
                    "15 minutes" -> timerDuration = 15 * 1000 * 60
                }

                updateTimerText(timerDuration)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        startBt.setOnClickListener{
            if (!isTimerRunning) {
                countDownTimer = object : CountDownTimer(timerDuration, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        updateTimerText(millisUntilFinished)
                    }

                    override fun onFinish() {
                        updateTimerText(0)
                        isTimerRunning = false
                    }
                }.start()

                isTimerRunning = true
            }
        }

        pauseBt.setOnClickListener{
            if (isTimerRunning) {
                countDownTimer.cancel()
                isTimerRunning = false
            }
        }

        resetBt.setOnClickListener{
            countDownTimer.cancel()
            isTimerRunning = false
            updateTimerText(timerDuration)
        }

    }
    private fun updateTimerText(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 1000 / 60
        val seconds = (millisUntilFinished / 1000) % 60
        val time = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = time
    }
}