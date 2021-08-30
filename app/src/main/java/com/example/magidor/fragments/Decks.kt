package com.example.magidor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magidor.Adapters.DecksItemRecyclerViewAdapter
import com.example.magidor.Adapters.MatchItemRecyclerViewAdapter
import com.example.magidor.R
import kotlinx.android.synthetic.main.games_fragment.view.*

class Decks : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.games_fragment, container, false)

        // Set the adapter
        with(view) {
            recycler_view.layoutManager = LinearLayoutManager(activity)
            //val itemAdapter = DecksItemRecyclerViewAdapter(mainActivity, mainActivity.getMatches())
            //recycler_view.adapter = itemAdapter
        }
        return view
    }
}