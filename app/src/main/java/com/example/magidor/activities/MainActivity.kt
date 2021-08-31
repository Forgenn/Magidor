package com.example.magidor.activities

//import com.example.kotlin.fragments.GamesFragment
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.magidor.fragments.Match
import com.example.magidor.Adapters.ViewPagerAdapter
import com.example.magidor.R
import com.example.magidor.data.Deck
import com.example.magidor.data.Game
import com.example.magidor.data.Player
import com.example.magidor.fragments.Players
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array


class MainActivity : AppCompatActivity() {

    var mainPlayer: Player = Player("Me")
    var opponents = arrayListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPlayer = readPlayerJson()
        opponents = readOpponentsJson()

        setUpTabs()
    }

    /**
     * Function is used to get the Items List which is added in the list.
     */
    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Match(), "Matches")
        adapter.addFragment(Players(), "Players")
        adapter.addFragment(Players(), "Stats")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    fun writePlayerJson(){
        val gson = Gson()
        val jsonMainPLayer = gson.toJson(mainPlayer)
        val jsonOpponents = gson.toJson(opponents)


        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putString("mainPlayer", jsonMainPLayer)
        editor?.putString("opponents", jsonOpponents)
        editor?.apply()
    }

    fun readPlayerJson(): Player{
        val gson = Gson()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        var player = Player("Me")

        val jsonMainPLayer: String? = sharedPref?.getString("mainPlayer", "defaultValue")

        if (jsonMainPLayer != "defaultValue"){
            player = gson.fromJson(jsonMainPLayer, Player::class.java)
        }

        return player
    }

    fun readOpponentsJson(): ArrayList<Player>{
        val gson = Gson()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        var opponents = arrayListOf<Player>()

        val jsonOpponents: String? = sharedPref?.getString("opponents", "defaultValue")

        val itemType = object : TypeToken<ArrayList<Player>>() {}.type

        if (jsonOpponents != "defaultValue"){
            opponents = gson.fromJson(jsonOpponents, itemType)
        }

        return opponents
    }

     fun getMatches(): ArrayList<Game> { //This will be json read
        return mainPlayer.games
    }

    fun addMatchMainPlayer(game: Game){
        mainPlayer.addGame(game)
    }

    fun addMatchOpponent(name: String, game: Game){
        val opponent: Player? = opponents.find { it.name == name }
        opponent!!.addGame(game)
    }

    fun addOpponent(opponent: Player){
        opponents.add(opponent)
    }

    fun getPossiblePlayersNames() : ArrayList<String>{
        val possibleOpponents = arrayListOf<String>()
        for (opponent in opponents){
            possibleOpponents.add(opponent.name)
        }
        return possibleOpponents
    }

    fun getPossibleMainPlayerDecks() : ArrayList<String>{
        val possibleDecks = arrayListOf<String>()
        for (mainDeck in mainPlayer.decks){
            possibleDecks.add(mainDeck.name)
        }
        return possibleDecks
    }

    fun getPossibleOpponentsDecks() : ArrayList<String>{
        val possibleDecks = arrayListOf<String>()
        opponents.forEach{it.decks.forEach { deck -> possibleDecks.add(deck.name) }}
        return possibleDecks
    }

    fun opponentHasDeck(playerName: String, deckName: String): Boolean{
        val hasDeck = opponents.find { it.name == playerName }?.decks?.find { it.name == deckName }
        return hasDeck != null
    }

    fun mainPlayerHasDeck(deckName: String): Boolean{
        val hasDeck = mainPlayer.decks.find { it.name == deckName }
        return hasDeck != null
    }

    fun getOpponentsDecks() : ArrayList<ArrayList<Deck>>{
        val opponentDecks : ArrayList<ArrayList<Deck>> = arrayListOf()
        for (opponent in opponents){
            opponentDecks.add(opponent.decks)
        }
        return opponentDecks
    }
}

