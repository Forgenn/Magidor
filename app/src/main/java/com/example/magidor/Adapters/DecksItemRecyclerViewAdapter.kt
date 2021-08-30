package com.example.magidor.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Deck
import com.example.magidor.data.Player
import com.example.magidor.databinding.DeckItemRowBinding

class DecksItemRecyclerViewAdapter(
    private val context: MainActivity,
    private val decks: ArrayList<Deck>,
) : RecyclerView.Adapter<DecksItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DeckItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deck = decks[position]

        holder.playerDeck.text = deck.name
        holder.cardView.setOnLongClickListener{ deleteItem(position)}
    }

    override fun getItemCount(): Int = decks.size

    private fun deleteItem(position: Int) : Boolean{
        decks.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        context.writePlayerJson()
        return true
    }

    inner class ViewHolder(binding: DeckItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val playerDeck = binding.playerDeckName
        val cardView = binding.cardViewItem

        override fun toString(): String {
            return super.toString()
        }
    }
}