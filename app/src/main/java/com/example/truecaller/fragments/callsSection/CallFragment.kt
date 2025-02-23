package com.example.truecaller.fragments.callsSection

import CallViewModel
import CallsPagerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.truecaller.R
import com.example.truecaller.views.DialerActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CallFragment : Fragment() {
    private lateinit var viewModel: CallViewModel
    private lateinit var dailerIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_call, container, false)
        viewModel = ViewModelProvider(this).get(CallViewModel::class.java)

        dailerIcon = view.findViewById(R.id.dailerIcon)
        dailerIcon.setOnClickListener {
            val intent = Intent(requireContext(), DialerActivity::class.java)
            startActivity(intent)
        }

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        val adapter = CallsPagerAdapter(this)
        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Primary"
                1 -> "All"
                2 -> "Promo"
                3 -> "Spam"
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }.attach()

        return view
    }
}