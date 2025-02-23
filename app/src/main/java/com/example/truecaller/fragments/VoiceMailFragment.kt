package com.example.truecaller.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truecaller.R
import com.example.truecaller.fragments.viewModels.VoiceMailViewModel

class VoiceMailFragment : Fragment() {

    companion object {
        fun newInstance() = VoiceMailFragment()
    }

    private val viewModel: VoiceMailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_voice_mail, container, false)
    }
}