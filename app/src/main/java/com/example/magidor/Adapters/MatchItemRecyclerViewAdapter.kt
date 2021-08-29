package com.example.magidor.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Game
import com.example.magidor.databinding.GameItemRowBinding


class MatchItemRecyclerViewAdapter(
    private val context: MainActivity,
    private val matches: ArrayList<Game>
) : RecyclerView.Adapter<MatchItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            GameItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = matches[position]

        holder.cardViewItem.setOnLongClickListener{ deleteItem(position)}
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

    private fun deleteItem(position: Int) : Boolean{
        matches.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        context.writePlayerJson()
        return true
    }

    override fun getItemCount(): Int = matches.size

    inner class ViewHolder(binding: GameItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItem: TextView = binding.tvItemName
        val tvResult: TextView = binding.tvItemResult
        val cardViewItem = binding.cardViewItem

        override fun toString(): String {
            return super.toString()
        }
    }
}