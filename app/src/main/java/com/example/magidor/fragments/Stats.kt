package com.example.magidor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Stat
import kotlinx.android.synthetic.main.players_fragment.view.*
import kotlinx.android.synthetic.main.stats_fragment.view.*
import kotlin.math.round

class Stats : Fragment() {

    private var mainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.stats_fragment, container, false)

        with (view){
            val mainPlayerStats: Stat = mainActivity.getMainPlayerStats()


            match_winrate_textview.text = "%.1f".format(mainPlayerStats.match_winrate) + "%"
            matches_played_textview.text = (mainPlayerStats.matches_won + mainPlayerStats.matches_lost).toString()
            matches_won_textview.text = mainPlayerStats.matches_won.toString()
            matches_lost_textview.text = mainPlayerStats.matches_lost.toString()

            games_winrate_textview.text = "%.1f".format(mainPlayerStats.game_winrate) + "%"
            games_played_textview2.text = (mainPlayerStats.games_won + mainPlayerStats.games_lost).toString()
            games_won_textview.text = mainPlayerStats.games_won.toString()
            games_lost_textview.text = mainPlayerStats.games_lost.toString()

        }
        return view
    }
}