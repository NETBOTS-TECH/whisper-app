package com.example.truecaller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.truecaller.fragments.callsSection.CallFragment
import com.example.truecaller.fragments.ContactFragment
import com.example.truecaller.fragments.MessageFragment
import com.example.truecaller.fragments.VoiceMailFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

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
}