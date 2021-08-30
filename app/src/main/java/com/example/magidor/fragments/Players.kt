package com.example.magidor.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magidor.Adapters.DecksItemRecyclerViewAdapter
import com.example.magidor.Adapters.PlayersItemRecyclerViewAdapter
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Deck
import com.example.magidor.data.Game
import com.example.magidor.data.Player
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.games_fragment.view.*
import kotlinx.android.synthetic.main.games_fragment.view.recycler_view
import kotlinx.android.synthetic.main.player_add_pop_up.view.*
import kotlinx.android.synthetic.main.player_item_row.view.*
import kotlinx.android.synthetic.main.players_fragment.view.*


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
            val itemAdapter = PlayersItemRecyclerViewAdapter(mainActivity, mainActivity.mainPlayer, mainActivity.opponents)
            recycler_view.adapter = itemAdapter


            var popupWindow: PopupWindow

            button_add_player.setOnClickListener{
                val inflater: LayoutInflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.player_add_pop_up, null)

                popupWindow = PopupWindow(
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
                    player_popup, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    -400 // Y offset
                )

                var addDeckButton = view.findViewById(R.id.button_add_deck) as Button
                var confirmPlayerButton = view.findViewById(R.id.button_confirm_player) as Button
                var cancelPlayerButton = view.findViewById(R.id.button_cancel_player) as Button

                var decks = arrayListOf<Deck>()

                addDeckButton.setOnClickListener {
                    var playerDeckEditText = view.findViewById(R.id.player_deck_editText) as EditText
                    val newDeck = playerDeckEditText.text.toString()
                    if (!newDeck.isNullOrBlank())
                        decks.add(Deck(newDeck))

                    Snackbar.make(
                        findViewById(R.id.mainLayout),
                        "Deck added to player",
                        Snackbar.LENGTH_LONG
                    ).show()

                    playerDeckEditText.setText("")
                }

                confirmPlayerButton.setOnClickListener {
                    var playerNameEditText = view.findViewById(R.id.player_name_editText) as EditText
                    var opponent = Player(
                        playerNameEditText.text.toString(),
                        arrayListOf<Game>(),
                        decks
                    )
                    mainActivity.addOpponent(opponent)
                    mainActivity.writePlayerJson()
                    itemAdapter.notifyDataSetChanged()
                    popupWindow.dismiss()
                }

                cancelPlayerButton.setOnClickListener {
                    popupWindow.dismiss()
                }

            }

            button_add_player.setOnClickListener {
                val inflater: LayoutInflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.player_add_pop_up, null)

                popupWindow = PopupWindow(
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
                    player_popup, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    -400 // Y offset
                )

                var addDeckButton = view.findViewById(R.id.button_add_deck) as Button
                var confirmPlayerButton = view.findViewById(R.id.button_confirm_player) as Button
                var cancelPlayerButton = view.findViewById(R.id.button_cancel_player) as Button

                var decks = arrayListOf<Deck>()

                addDeckButton.setOnClickListener {
                    var playerDeckEditText = view.findViewById(R.id.player_deck_editText) as EditText
                    val newDeck = playerDeckEditText.text.toString()
                    if (!newDeck.isNullOrBlank())
                        decks.add(Deck(newDeck))

                    Snackbar.make(
                        findViewById(R.id.mainLayout),
                        "Deck added to player",
                        Snackbar.LENGTH_LONG
                    ).show()

                    playerDeckEditText.setText("")
                }

                confirmPlayerButton.setOnClickListener {
                    var playerNameEditText = view.findViewById(R.id.player_name_editText) as EditText
                    var opponent = Player(
                        playerNameEditText.text.toString(),
                        arrayListOf<Game>(),
                        decks
                    )
                    mainActivity.addOpponent(opponent)
                    mainActivity.writePlayerJson()
                    itemAdapter.notifyDataSetChanged()
                    popupWindow.dismiss()
                }

                cancelPlayerButton.setOnClickListener {
                    popupWindow.dismiss()
                }
            }
        }
        return view
    }
}