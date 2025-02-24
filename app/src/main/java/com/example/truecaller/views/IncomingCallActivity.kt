package com.example.truecaller.views

import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.truecaller.R
import com.example.truecaller.fragments.viewModels.TextToSpeechViewModel

class IncomingCallActivity : AppCompatActivity() {

    private val ttsViewModel: TextToSpeechViewModel by viewModels()


    private lateinit var callerNameTextView: TextView
    private lateinit var callerNumberTextView: TextView
    private lateinit var acceptCallButton: Button
    private lateinit var rejectCallButton: Button
    private lateinit var endCallButton: Button

    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_call)

        val callerName = intent.getStringExtra("callerName")
        val callerNumber = intent.getStringExtra("callerNumber")

        ttsViewModel.isReady.observe(this, Observer { isReady ->
            acceptCallButton.isEnabled = isReady
        })

        callerNameTextView = findViewById(R.id.callerName)
        callerNumberTextView = findViewById(R.id.callerNumber)
        acceptCallButton = findViewById(R.id.acceptCallButton)
        rejectCallButton = findViewById(R.id.rejectCallButton)
        endCallButton = findViewById(R.id.endCallButton)

        playSystemRingtone()


        acceptCallButton.setOnClickListener {
            startCall()
        }

        rejectCallButton.setOnClickListener {
            rejectCall()
        }

        endCallButton.setOnClickListener {
            endCall()
        }
    }

    private fun playSystemRingtone() {
        val uri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ringtone = RingtoneManager.getRingtone(applicationContext, uri)
        ringtone?.play()
    }


    private fun startCall() {
        ringtone?.stop()

        acceptCallButton.visibility = View.GONE
        rejectCallButton.visibility = View.GONE
        endCallButton.visibility = View.VISIBLE
        callerNameTextView.text = "Call in Progress..."
        ttsViewModel.speak("Hello there, how are you? You have won, 2 million rupees")
        println("Call accepted")

    }

    private fun rejectCall() {
        ringtone?.stop()
        finish()

        println("Call rejected")
    }

    private fun endCall() {
        ringtone?.stop()
        finish()

        println("Call ended")
    }
}