package com.example.magidor.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magidor.Adapters.MatchItemRecyclerViewAdapter
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Deck
import com.example.magidor.data.Game
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.game_add_pop_up.view.*
import kotlinx.android.synthetic.main.games_fragment.*
import kotlinx.android.synthetic.main.games_fragment.view.*

/**
 * A fragment representing a list of Items.
 */


class Match : Fragment() {

    private var mainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.games_fragment, container, false)

        // Set the adapter
        with(view) {
            recycler_view.layoutManager = LinearLayoutManager(activity)
            val itemAdapter = MatchItemRecyclerViewAdapter(mainActivity, mainActivity.getMatches())
            recycler_view.adapter = itemAdapter

            var popupWindow: PopupWindow


            button_add_game.setOnClickListener {
                    val inflater: LayoutInflater =
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val view = inflater.inflate(R.layout.game_add_pop_up, null)

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
                        game_popup, // Location to display popup window
                        Gravity.CENTER, // Exact position of layout to display popup
                        0, // X offset
                        0 // Y offset
                    )

                    val result_button = view.findViewById(R.id.button_add_deck_inner) as Button
                    val button_confirm = view.findViewById(R.id.button_confirm) as Button
                    val button_cancel = view.findViewById(R.id.button_cancel) as Button


                    //Autocomplete text views declarations
                    val autocompleteTextViewOpponent = view.findViewById(R.id.autocomplete_opponent) as AutoCompleteTextView
                    val possibleOpponents : ArrayList<String> = mainActivity.getPossiblePlayersNames()
                    autocompleteTextViewOpponent.setAdapter(ArrayAdapter(mainActivity, android.R.layout.simple_list_item_1, possibleOpponents))

                    val autoTextDeck1 = view.findViewById(R.id.text_deck1) as AutoCompleteTextView
                    val possibleMainPlayerDecks : ArrayList<String> = mainActivity.getPossibleMainPlayerDecks()
                    autoTextDeck1.setAdapter(ArrayAdapter(mainActivity, android.R.layout.simple_list_item_1, possibleMainPlayerDecks))

                    val autoTextDeck2 = view.findViewById(R.id.text_deck2) as AutoCompleteTextView
                    val possibleOpponentsDecks : ArrayList<String> = mainActivity.getPossibleOpponentsDecks()
                    autoTextDeck2.setAdapter(ArrayAdapter(mainActivity, android.R.layout.simple_list_item_1, possibleOpponentsDecks))


                    result_button.setOnClickListener {
                        val popupMenu: PopupMenu = PopupMenu(view.context, result_button)
                        popupMenu.menuInflater.inflate(R.menu.possible_match_results, popupMenu.menu)

                        popupMenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                else -> result_button.text = item.title
                            }
                            true
                        }
                        popupMenu.show()
                    }

                    button_confirm.setOnClickListener {
                        //There has to be a better way to handle this
                        if (result_button.text == "Result") {
                            Snackbar.make(
                                findViewById(R.id.mainLayout),
                                "You have to input the result of the match",
                                Snackbar.LENGTH_LONG
                            ).show()
                            popupWindow.dismiss()
                            view.hideKeyboard()
                            return@setOnClickListener
                        }

                        val playerName = autocompleteTextViewOpponent.text.toString()
                        val deck1Name = autoTextDeck1.text.toString()
                        val deck2Name = autoTextDeck2.text.toString()

                        if (playerName !in possibleOpponents){
                            Snackbar.make(
                                findViewById(R.id.mainLayout),
                                "No player with that name found",
                                Snackbar.LENGTH_LONG
                            ).show()
                            view.hideKeyboard()
                            return@setOnClickListener
                        }

                        if (deck1Name !in possibleMainPlayerDecks) {
                            Snackbar.make(
                                findViewById(R.id.mainLayout),
                                "You don't have that deck, add it in the Players tab",
                                Snackbar.LENGTH_LONG
                            ).show()
                            view.hideKeyboard()
                            return@setOnClickListener
                        }

                        if (!mainActivity.opponentHasDeck(playerName, deck2Name)){
                            Snackbar.make(
                                findViewById(R.id.mainLayout),
                                "The opponent doesn't have that deck, add it in the Players tab",
                                Snackbar.LENGTH_LONG
                            ).show()
                            view.hideKeyboard()
                            return@setOnClickListener
                        }


                        val (game_score_one, game_score_two) = result_button.text.split("-")

                        val playOrDrawSwitch = view.findViewById(R.id.play_or_draw_switch) as SwitchMaterial

                        val game = Game(
                            Pair(game_score_one.toInt(), game_score_two.toInt()),
                            Deck(deck1Name),
                            Deck(deck2Name),
                            !playOrDrawSwitch.isChecked
                        )
                        //Set match in reverse for opponent
                        val gameOpponent = Game(
                            Pair(game_score_two.toInt(), game_score_one.toInt()),
                            Deck(deck2Name),
                            Deck(deck1Name),
                            playOrDrawSwitch.isChecked
                        )

                        mainActivity.addMatchOpponent(autocompleteTextViewOpponent.text.toString(), gameOpponent)
                        mainActivity.addMatchMainPlayer(game)
                        mainActivity.writePlayerJson()
                        itemAdapter.notifyDataSetChanged()
                        popupWindow.dismiss()
                    }

                    button_cancel.setOnClickListener {
                        popupWindow.dismiss()
                    }
                }
        }

        return view
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}