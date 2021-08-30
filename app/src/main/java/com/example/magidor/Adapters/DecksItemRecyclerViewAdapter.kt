package com.example.magidor.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magidor.data.Deck
import com.example.magidor.data.Player
import com.example.magidor.databinding.PlayerAddPopUpBinding

class DecksItemRecyclerViewAdapter(
    private val context: Context,
    private val decks: ArrayList<Deck>,
) : RecyclerView.Adapter<DecksItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            PlayerAddPopUpBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = decks.size

    inner class ViewHolder(binding: PlayerAddPopUpBinding) : RecyclerView.ViewHolder(binding.root) {

        override fun toString(): String {
            return super.toString()
        }
    }
}