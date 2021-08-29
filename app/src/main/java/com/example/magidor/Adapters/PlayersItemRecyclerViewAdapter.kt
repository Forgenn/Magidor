package com.example.magidor.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.magidor.R
import com.example.magidor.data.Game
import com.example.magidor.data.Player
import com.example.magidor.databinding.PlayerItemRowBinding


class PlayersItemRecyclerViewAdapter(
    private val context: Context,
    private val mainPlayer: Player,
    private val opponents: ArrayList<Player>,
) : RecyclerView.Adapter<PlayersItemRecyclerViewAdapter.ViewHolder>() {

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
        var player: Player = Player("bruh")

        if (position == 0){
            player = mainPlayer
        } else if (opponents.isNotEmpty()) {
            player = opponents[position]
        }

        holder.playerName.text = player.name

    }

    override fun getItemCount(): Int = opponents.size + 1

    inner class ViewHolder(binding: PlayerItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val playerName = binding.tvPlayerName
        val cardViewItem = binding.cardViewItem

        override fun toString(): String {
            return super.toString()
        }
    }

}