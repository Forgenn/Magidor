package com.example.magidor.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magidor.Adapters.MatchItemRecyclerViewAdapter
import com.example.magidor.R
import com.example.magidor.activities.MainActivity
import com.example.magidor.data.Game
import com.google.android.material.snackbar.Snackbar
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

                    var result_button = view.findViewById(R.id.button_add_deck_inner) as Button
                    var button_confirm = view.findViewById(R.id.button_confirm) as Button
                    var button_cancel = view.findViewById(R.id.button_cancel) as Button

                    result_button.setOnClickListener {
                        val popupMenu: PopupMenu = PopupMenu(view.context, result_button)
                        popupMenu.menuInflater.inflate(R.menu.possible_match_results, popupMenu.menu)

                        popupMenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.two_one ->
                                    result_button.text = item.title
                                R.id.two_zero ->
                                    result_button.text = item.title
                                R.id.zero_two ->
                                    result_button.text = item.title
                                R.id.one_two ->
                                    result_button.text = item.title
                                R.id.one_zero ->
                                    result_button.text = item.title
                                R.id.zero_one ->
                                    result_button.text = item.title
                            }
                            true
                        }
                        popupMenu.show()
                    }

                    button_confirm.setOnClickListener {
                        //Afegir item a llista
                        if (result_button.text == "Result") {
                            Snackbar.make(
                                findViewById(R.id.mainLayout),
                                "Pero a veure, si no fiques el resultat que creus que pasara?",
                                Snackbar.LENGTH_LONG
                            ).show()
                            popupWindow.dismiss()
                            return@setOnClickListener
                        }
                        val (game_score_one, game_score_two) = result_button.text.split("-")

                        var deck1 = view.findViewById(R.id.text_deck1) as EditText
                        var deck2 = view.findViewById(R.id.text_deck2) as EditText

                        var playOrDrawSwitch = view.findViewById(R.id.play_or_draw_switch) as Switch

                        var game = Game(
                            Pair(game_score_one.toInt(), game_score_two.toInt()),
                            deck1.text.toString(),
                            deck2.text.toString(),
                            !playOrDrawSwitch.isChecked
                        )
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
}