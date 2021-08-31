package com.example.magidor.Adapters

import android.content.Context
import android.os.Build
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Deck
import com.example.magidor.data.Player
import com.example.magidor.databinding.DeckItemRowBinding
import com.example.magidor.databinding.DeckAddButtonBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.players_fragment.*
import kotlinx.android.synthetic.main.players_fragment.view.*

class DecksItemRecyclerViewAdapter(
    private val context: MainActivity,
    private val decks: ArrayList<Deck>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == R.layout.deck_item_row) {
            ViewHolderDeck(
                DeckItemRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ViewHolderAdd(
                DeckAddButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }



    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolderDeck -> {
                val deck = decks[position]
                holder.playerDeck.text = deck.name
                holder.cardView.setOnLongClickListener{ deleteItem(position)}
            }
            is ViewHolderAdd -> {
                holder.addDeck.setOnClickListener { showPopUpAddDeck() }
            }
        }

    }

    override fun getItemCount(): Int = decks.size + 1

    private fun deleteItem(position: Int) : Boolean{
        decks.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - 1)
        context.writePlayerJson()
        return true
    }

    //if last item, show add deck button instead of deck
    override fun getItemViewType(position: Int): Int{
        return if (position == itemCount - 1) R.layout.deck_add_button else R.layout.deck_item_row
    }

    fun showPopUpAddDeck(){
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.deck_add_new_deck, null)

        var popupWindow: PopupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }

        popupWindow.isTouchable = true
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true

        popupWindow.showAtLocation(
            context.player_popup, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            -400 // Y offset
        )

        var addDeckButtonOnly = view.findViewById(R.id.button_add_deck_only) as Button
        var confirmPlayerButtonOnly = view.findViewById(R.id.button_confirm_deck_only) as Button
        var cancelPlayerButtonOnly = view.findViewById(R.id.button_cancel_deck_only) as Button

        var newDecks = arrayListOf<Deck>()

        addDeckButtonOnly.setOnClickListener {
            var playerDeckEditText = view.findViewById(R.id.deck_only_editText) as EditText
            val newDeck = playerDeckEditText.text.toString()
            if (!newDeck.isNullOrBlank())
                newDecks.add(Deck(newDeck))

            Snackbar.make(
                context.findViewById(R.id.mainLayout),
                "Deck added to player",
                Snackbar.LENGTH_LONG
            ).show()

            playerDeckEditText.setText("")
        }

        confirmPlayerButtonOnly.setOnClickListener {
            decks.addAll(newDecks)
            context.writePlayerJson()
            notifyDataSetChanged()
            popupWindow.dismiss()
        }

        cancelPlayerButtonOnly.setOnClickListener {
            popupWindow.dismiss()
        }

    }

    inner class ViewHolderDeck(binding: DeckItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val playerDeck = binding.playerDeckName
        val cardView = binding.cardViewItem

        override fun toString(): String {
            return super.toString()
        }
    }

    inner class ViewHolderAdd(binding: DeckAddButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        val addDeck = binding.buttonAddDeckInner

        override fun toString(): String {
            return super.toString()
        }
    }
}