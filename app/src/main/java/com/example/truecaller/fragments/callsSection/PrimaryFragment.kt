package com.example.truecaller.fragments.callsSection

import CallAdapter
import CallViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.truecaller.R


class PrimaryFragment : Fragment() {

    private lateinit var viewModel: CallViewModel
    private lateinit var adapter: CallAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_primary, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(CallViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CallAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.primaryCalls.observe(viewLifecycleOwner) { calls ->
            adapter = CallAdapter(calls)
            recyclerView.adapter = adapter
        }

        return view
    }
}
