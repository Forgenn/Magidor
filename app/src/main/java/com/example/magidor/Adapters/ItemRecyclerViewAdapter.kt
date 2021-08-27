package com.example.magidor.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.magidor.R
import com.example.magidor.data.Game
import com.example.magidor.databinding.FragmentItemBinding


class ItemRecyclerViewAdapter(
    private val context: Context,
    private val values: ArrayList<Game>
) : RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.tvItem.text = item.deck_one + " vs " + item.deck_two
        holder.tvResult.text = item.game_score.first.toString() + "-" + item.game_score.second.toString()

        // Updating the background color according to the odd/even positions in list.

        if (item.game_score.first  > item.game_score.second) {
            holder.cardViewItem.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.purple_500
                )
            )
        } else {
            holder.cardViewItem.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.red
                )
            )
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItem: TextView = binding.tvItemName
        val tvResult: TextView = binding.tvItemResult
        val cardViewItem = binding.cardViewItem

        override fun toString(): String {
            return super.toString()
        }
    }

}