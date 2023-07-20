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

    private lateinit var startBt : Button
    private lateinit var pauseBt : Button
    private lateinit var resetBt : Button
    private lateinit var timerTextView: TextView
    private lateinit var spinX : Spinner
    var timerDuration : Long = 0
    var remainingTime : Long = 0
    var isTimerRunning = false
    private lateinit var countDownTimer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBt = findViewById(R.id.startBt)
        pauseBt = findViewById(R.id.pauseBt)
        resetBt = findViewById(R.id.resetBt)
        timerTextView= findViewById(R.id.timerTextView)
        spinX = findViewById(R.id.spinnerTime)

        val choices = arrayOf(5,10,15)
        //val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, choices)
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinX.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, choices)

        spinX.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val x = choices[p2]
                timerDuration = ((x * 1000 * 60).toLong())
                updateTimerText(timerDuration)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        startBt.setOnClickListener{
            if (!isTimerRunning) {
                countDownTimer = object : CountDownTimer(
                    if (remainingTime > 0) remainingTime else timerDuration,
                    1000
                ) {
                    override fun onTick(millisUntilFinished: Long) {
                        remainingTime = millisUntilFinished
                        updateTimerText(remainingTime)
                    }

                    override fun onFinish() {
                        remainingTime = 0
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
            remainingTime = 0L
            updateTimerText(timerDuration)
        }

    }
    private fun updateTimerText(millisUntilFinished: Long){
        val minutes = millisUntilFinished / 1000 / 60
        val seconds = (millisUntilFinished / 1000) % 60
        val time = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = time
    }
}