package com.example.kotlin.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat.getSystemService
import com.example.kotlin.Adapters.ItemRecyclerViewAdapter
import com.example.kotlin.R
import com.example.kotlin.data.Game
import com.example.kotlin.data.Player
import com.example.kotlin.popupWindow
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.games_fragment.*
import kotlinx.android.synthetic.main.games_fragment.view.*

/**
 * A fragment representing a list of Items.
 */

var mainPlayer = Player()
class ItemFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.games_fragment, container, false)

        // Set the adapter
        with(view) {
            recycler_view.layoutManager = LinearLayoutManager(activity)
            val itemAdapter = ItemRecyclerViewAdapter(context, getItemsList())
            recycler_view.adapter = itemAdapter

            button_add_game.setOnClickListener {
                val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.activity_game_pop_up,null)

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
                var result_button = view.findViewById(R.id.result_button) as Button
                var button_confirm = view.findViewById(R.id.button_confirm) as Button
                var button_cancel = view.findViewById(R.id.button_cancel) as Button


                result_button.setOnClickListener {
                    val popupMenu: PopupMenu = PopupMenu(view.context, result_button)
                    popupMenu.menuInflater.inflate(R.menu.possible_results, popupMenu.menu)

                    popupMenu.setOnMenuItemClickListener {
                            item -> when(item.itemId){
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
                    if (result_button.text == "Result"){
                        Snackbar.make(findViewById(R.id.mainLayout), "Pero a veure, si no fiques el resultat que creus que pasara?", Snackbar.LENGTH_LONG).show()
                        popupWindow.dismiss()
                        return@setOnClickListener
                    }
                    val (game_score_one, game_score_two) = result_button.text.split("-")

                    var deck1 = view.findViewById(R.id.text_deck1) as EditText
                    var deck2 = view.findViewById(R.id.text_deck2) as EditText

                    var game = Game(mainPlayer, Pair(game_score_one.toInt(), game_score_two.toInt()), deck1.text.toString(), deck2.text.toString())
                    mainPlayer.addGame(game)
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

    private fun getItemsList(): ArrayList<Game> { //This will be json read
        return mainPlayer.games
    }
}