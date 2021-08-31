package com.example.magidor.Adapters

import android.content.Context
import android.transition.TransitionManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Deck
import com.example.magidor.data.Game
import com.example.magidor.data.Player
import com.example.magidor.databinding.PlayerItemRowBinding
import com.google.android.material.snackbar.Snackbar


class PlayersItemRecyclerViewAdapter(
    private val context: MainActivity,
    private val mainPlayer: Player,
    private val opponents: ArrayList<Player>,
) : RecyclerView.Adapter<PlayersItemRecyclerViewAdapter.ViewHolder>() {

    var expandedPosition = -1
    var previousExpandedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            PlayerItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Subitem recyclerview
        holder.recyclerViewDeck.layoutManager = LinearLayoutManager(context as MainActivity)
        val deckAdapter = DecksItemRecyclerViewAdapter(context, getOpponentDecksByPosition(position))
        holder.recyclerViewDeck.adapter = deckAdapter

        //Show sublist
        var isExpanded = position == expandedPosition
        if (isExpanded)
            previousExpandedPosition = position;

        holder.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.cardViewItem.setOnClickListener{
            expandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }

        //Set text
        var player: Player = Player("bruh")

        if (position == 0){
            player = mainPlayer
        } else if (opponents.isNotEmpty()) {
            player = opponents[position - 1]
        }

        holder.playerName.text = player.name
        holder.cardViewItem.setOnLongClickListener { deleteItem(position) }

    }

    private fun deleteItem(position: Int) : Boolean{
        if (position == 0){
            Snackbar.make(
                context.findViewById(R.id.mainLayout),
                "Cant remove yourself",
                Snackbar.LENGTH_LONG
            ).show()
        } else if (opponents.isNotEmpty()) {
            opponents.removeAt(position - 1)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            context.writePlayerJson()
        }
        return true
    }

    override fun getItemCount(): Int = opponents.size + 1

    private fun getOpponentDecksByPosition(position: Int) : ArrayList<Deck>{
        return if (position == 0) mainPlayer.decks else opponents[position - 1].decks
    }

    inner class ViewHolder(binding: PlayerItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val playerName = binding.tvPlayerName
        val expandableLayout = binding.expandableLayout
        val cardViewItem = binding.cardViewItem
        val recyclerViewDeck = binding.recyclerViewDeck


        override fun toString(): String {
            return super.toString()
        }
    }



}