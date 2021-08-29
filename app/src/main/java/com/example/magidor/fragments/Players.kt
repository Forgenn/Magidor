package com.example.magidor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magidor.Adapters.PlayersItemRecyclerViewAdapter
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import kotlinx.android.synthetic.main.games_fragment.view.*


class Players : Fragment() {

    private var mainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.players_fragment, container, false)

        with(view) {
            recycler_view.layoutManager = LinearLayoutManager(activity)
            val itemAdapter = PlayersItemRecyclerViewAdapter(context, mainActivity.mainPlayer, mainActivity.opponents)
            recycler_view.adapter = itemAdapter
        }
        return view
    }
}