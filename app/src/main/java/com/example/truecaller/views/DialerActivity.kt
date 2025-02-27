package com.example.truecaller.views

import SocketViewModel
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.truecaller.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import setSafeOnClickListener
import java.util.concurrent.Delayed

class DialerActivity : AppCompatActivity() {

    private lateinit var numberDisplay: TextView
    private lateinit var normalCallBtn: Button
    private lateinit var spamCallBtn: Button
    private lateinit var socketViewModel: SocketViewModel
    private var dialedNumber = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dailer)

        numberDisplay = findViewById(R.id.numberDisplay)
        normalCallBtn = findViewById(R.id.callButton)
        spamCallBtn = findViewById(R.id.spamCallButton)

        socketViewModel = ViewModelProvider(this)[SocketViewModel::class.java]

        normalCallBtn.setSafeOnClickListener {
            val phoneNumber = dialedNumber.toString()
            if (phoneNumber.isNotEmpty()) {
                val intent = Intent(this, IncomingCallActivity::class.java).apply {
                    putExtra("callerName", "callerName")
                    putExtra("callType", "normal")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                socketViewModel.startCall()
                Toast.makeText(this, "Calling $phoneNumber", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }

        spamCallBtn.setSafeOnClickListener {
            val phoneNumber = dialedNumber.toString()
            if (phoneNumber.isNotEmpty()) {
                val intent = Intent(this, IncomingCallActivity::class.java).apply {
                    putExtra("callerName", "callerName")
                    putExtra("callType", "spam")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                Toast.makeText(this, "Calling $phoneNumber", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }
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


    private fun updateNumberDisplay() {
        numberDisplay.text = dialedNumber.toString()
    }
}