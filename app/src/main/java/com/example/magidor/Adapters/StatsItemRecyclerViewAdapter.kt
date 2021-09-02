package com.example.magidor.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Deck
import com.example.magidor.data.Player
import com.example.magidor.databinding.StatsItemRowBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.stats_fragment.view.*

class StatsItemRecyclerViewAdapter(
    private val context: MainActivity,
    private val decks: ArrayList<String>,
) : RecyclerView.Adapter<StatsItemRecyclerViewAdapter.ViewHolder>() {

    var expandedPosition = -1
    var previousExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            StatsItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deck = decks[position]

        var isExpanded = position == expandedPosition
        if (isExpanded)
            previousExpandedPosition = position

        holder.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.deckNameCard.setOnClickListener{
            expandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }

        holder.deckName.text = deck

        val stats = context.getMainPlayerDeckStats(deck)

        holder.matchWinrate.text = "%.1f".format(stats.match_winrate) + "%"
        holder.matchesPlayed.text = (stats.matches_won + stats.matches_lost).toString()
        holder.matchesWon.text = stats.matches_won.toString()
        holder.matchesLost.text = stats.matches_lost.toString()

        holder.gameWinrate.text = "%.1f".format(stats.game_winrate) + "%"
        holder.gamesPlayed.text = (stats.games_won + stats.games_lost).toString()
        holder.gamesWon.text = stats.games_won.toString()
        holder.gamesLost.text = stats.games_lost.toString()

        holder.textTitle.text = deck + " Stats"

    }


    override fun getItemCount(): Int = decks.size

    inner class ViewHolder(binding: StatsItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val deckName = binding.playerDeckName
        val deckStats = binding.cardViewItemStats
        val deckNameCard = binding.cardViewItem
        val expandableLayout = binding.expandableLayout
        val textTitle = binding.textView4

        val matchWinrate = binding.matchWinrateTextview
        val matchesPlayed = binding.matchesPlayedTextview
        val matchesWon = binding.matchesWonTextview
        val matchesLost = binding.matchesLostTextview

        val gameWinrate = binding.gamesWinrateTextview
        val gamesPlayed = binding.gamesPlayedTextview2
        val gamesWon = binding.gamesWonTextview
        val gamesLost = binding.gamesLostTextview

    }
}