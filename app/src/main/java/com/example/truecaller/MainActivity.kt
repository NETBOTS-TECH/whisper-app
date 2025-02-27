package com.example.truecaller

import SocketViewModel
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.truecaller.fragments.callsSection.CallFragment
import com.example.truecaller.fragments.ContactFragment
import com.example.truecaller.fragments.messageSection.MessageFragment
import com.example.truecaller.fragments.VoiceMailFragment
import com.example.truecaller.views.IncomingCallActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SocketViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        viewModel = ViewModelProvider(this)[SocketViewModel::class.java]



        checkMicrophonePermission()


        // Observe LiveData
        viewModel.message.observe(this, Observer { message ->
            Log.d("socket", "MainAct---> message: $message")
        })

        viewModel.isRinging.observe(this, Observer { isRinging ->
            Log.d("socket", "MainAct---> isRinging: $isRinging")

            if (isRinging) {
                val intent = Intent(this, IncomingCallActivity::class.java).apply {
                    putExtra("callerName", "callerName")
                    putExtra("callType", "normal")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
            } else {

            }
        })




        viewModel.inCall.observe(this, Observer { inCall ->
            Log.d("socket", "MainAct---> inCall: $inCall")

        })

        viewModel.transcription.observe(this, Observer { transcription ->
            Toast.makeText(this, transcription, Toast.LENGTH_LONG).show()

            Log.d("socket", "MainAct---> transcription: $transcription")

        })



        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_calls -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CallFragment())
                        .commit()
                    true
                }

                R.id.navigation_messages -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MessageFragment())
                        .commit()
                    true
                }

                R.id.navigation_contacts -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ContactFragment())
                        .commit()
                    true
                }

                R.id.navigation_voicemail -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, VoiceMailFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }

        bottomNavigation.selectedItemId = R.id.navigation_calls


    }

    private fun checkMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                101
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permission", "Microphone permission granted")
            } else {
                Log.e("Permission", "Microphone permission denied")
            }
        }
    }

}
