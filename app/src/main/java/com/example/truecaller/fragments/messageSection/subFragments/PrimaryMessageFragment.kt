package com.example.truecaller.fragments.messageSection.subFragments

import CallAdapter
import CallViewModel
import Message
import MessageViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.truecaller.R
import com.example.truecaller.fragments.messageSection.adapters.MessageAdapter


class PrimaryMessageFragment : Fragment() {

    private lateinit var viewModel: MessageViewModel
    private lateinit var adapter: MessageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_primary, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(MessageViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MessageAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.primaryMessages.observe(viewLifecycleOwner) { messages ->
            adapter = MessageAdapter(messages)
            recyclerView.adapter = adapter
        }

        return view
    }
}
