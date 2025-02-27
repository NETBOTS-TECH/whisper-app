package com.example.truecaller.views

import SocketViewModel
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer

import com.example.truecaller.R
import com.example.truecaller.fragments.viewModels.TextToSpeechViewModel
import java.io.File

class IncomingCallActivity : AppCompatActivity() {

    private val ttsViewModel: TextToSpeechViewModel by viewModels()
    private val viewModel: SocketViewModel by viewModels()

    private var recorder: MediaRecorder? = null
    private var audioFile: File? = null
    private val handler = Handler(Looper.getMainLooper())


    private lateinit var callerNameTextView: TextView
    private lateinit var callerNumberTextView: TextView
    private lateinit var callTypeTextView: TextView
    private lateinit var acceptCallButton: Button
    private lateinit var rejectCallButton: Button
    private lateinit var endCallButton: Button

    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_call)

        val callerName = intent.getStringExtra("callerName")
        val callerType = intent.getStringExtra("callType")

        ttsViewModel.isReady.observe(this, Observer { isReady ->
//            acceptCallButton.isEnabled = isReady
        })
        viewModel.transcription.observe(this, Observer { transcription ->
            Toast.makeText(this, transcription, Toast.LENGTH_LONG).show()
        })

        callerNameTextView = findViewById(R.id.callerName)
        callerNumberTextView = findViewById(R.id.callerNumber)
        acceptCallButton = findViewById(R.id.acceptCallButton)
        rejectCallButton = findViewById(R.id.rejectCallButton)
        endCallButton = findViewById(R.id.endCallButton)
        callTypeTextView = findViewById(R.id.callType)

        if (callerType.equals("spam")) {
            callTypeTextView.text = "Spam Call"
            callerNameTextView.setTextColor(ContextCompat.getColor(this, R.color.red))
            callerNumberTextView.setTextColor(ContextCompat.getColor(this, R.color.red))
        }

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
        viewModel.acceptCall()
        ringtone?.stop()
        startRecording()
        handler.postDelayed({
            stopRecording()
            audioFile?.let {
                viewModel.sendAudioFile(it) // Send after recording
            }
        }, 3000) //

        acceptCallButton.visibility = View.GONE
        rejectCallButton.visibility = View.GONE
        endCallButton.visibility = View.VISIBLE
        callerNameTextView.text = "Call in Progress..."
        println("Call accepted")

    }

    private fun rejectCall() {
        ringtone?.stop()
        finish()

        println("Call rejected")
    }

    private fun endCall() {
        viewModel.endCall()
        ringtone?.stop()

        finish()

        println("Call ended")
    }

    private fun startRecording() {
        audioFile = File(getExternalFilesDir(null), "call_audio.mp3")

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(audioFile!!.absolutePath)

            try {
                prepare()
                start()
                Log.d("socket", "startRecording: ")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRecording() {
        try {
            recorder?.apply {
                stop()
                release()
            }
            recorder = null
            Log.d(
                "socket",
                "stopRecording: Recording stopped and saved at: ${audioFile?.absolutePath}"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        ringtone?.stop()
        ringtone?.stop()
        recorder?.release()
        super.onDestroy()
    }
}