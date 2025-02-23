package com.example.truecaller.views

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.truecaller.R

class DialerActivity : AppCompatActivity() {

    private lateinit var numberDisplay: TextView
    private var dialedNumber = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dailer)

         numberDisplay = findViewById(R.id.numberDisplay)
    }

     fun onDigitClick(view: View) {
        val button = view as Button
        val digit = button.text.toString()
        dialedNumber.append(digit)
        updateNumberDisplay()
    }

    fun onBackspaceClick(view: View) {
        if (dialedNumber.isNotEmpty()) {
            dialedNumber.deleteCharAt(dialedNumber.length - 1)
            updateNumberDisplay()
        }
    }

    fun onCallClick(view: View) {
        val phoneNumber = dialedNumber.toString()
        if (phoneNumber.isNotEmpty()) {
            Toast.makeText(this, "Calling $phoneNumber", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNumberDisplay() {
        numberDisplay.text = dialedNumber.toString()
    }
}