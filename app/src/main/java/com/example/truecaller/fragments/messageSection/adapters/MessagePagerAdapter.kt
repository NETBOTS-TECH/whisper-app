package com.example.truecaller.fragments.messageSection.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.truecaller.fragments.callsSection.subFragments.AllFragment
import com.example.truecaller.fragments.callsSection.subFragments.PrimaryFragment
import com.example.truecaller.fragments.callsSection.subFragments.PromoFragment
import com.example.truecaller.fragments.callsSection.subFragments.SpamFragment
import com.example.truecaller.fragments.messageSection.subFragments.AllMessageFragment
import com.example.truecaller.fragments.messageSection.subFragments.PrimaryMessageFragment
import com.example.truecaller.fragments.messageSection.subFragments.PromoMessageFragment
import com.example.truecaller.fragments.messageSection.subFragments.SpamMessageFragment

class MessagePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PrimaryMessageFragment()
            1 -> AllMessageFragment()
            2 -> PromoMessageFragment()
            3 -> SpamMessageFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}