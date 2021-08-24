package com.example.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.data.Game
import com.example.kotlin.data.Player
import com.recyclerviewapp.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the LayoutManager that this RecyclerView will use.
        recycler_view_items.layoutManager = LinearLayoutManager(this)

        // Adapter class is initialized and list is passed in the param.
        val itemAdapter = ItemAdapter(this, getItemsList())


        // adapter instance is set to the recyclerview to inflate the items.
        recycler_view_items.adapter = itemAdapter


        //itemAdapter.items.add()
    }

    /**
     * Function is used to get the Items List which is added in the list.
     */
    private fun getItemsList(): ArrayList<Game> { //This will be json read
        val player = Player("Pol", arrayListOf<Game>())
        for (i in 1..15) {
            player.addGame(
                Game(
                    player,
                    Player("Oponent", arrayListOf<Game>()),
                    Pair<Int, Int>(2, 1),
                    "Delver",
                    "Goglari"
                )
            )
        }
        return player.games
    }
}