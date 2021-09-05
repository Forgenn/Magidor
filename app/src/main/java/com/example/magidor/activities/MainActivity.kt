package com.example.magidor.activities

//import com.example.kotlin.fragments.GamesFragment
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.magidor.Adapters.ViewPagerAdapter
import com.example.magidor.R
import com.example.magidor.data.Deck
import com.example.magidor.data.Game
import com.example.magidor.data.Player
import com.example.magidor.data.Stat
import com.example.magidor.fragments.Match
import com.example.magidor.fragments.Players
import com.example.magidor.fragments.Stats
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*


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
        adapter.addFragment(Stats(), "Stats")
        viewPager.adapter = adapter

        tabs.setupWithViewPager(viewPager)
        tabs.setTabTextColors(Color.parseColor("#a39a58"), Color.parseColor("#b5aa59"))
    }

    fun writePlayerJson() {
        val gson = Gson()
        val jsonMainPLayer = gson.toJson(mainPlayer)
        val jsonOpponents = gson.toJson(opponents)


        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putString("mainPlayer", jsonMainPLayer)
        editor?.putString("opponents", jsonOpponents)
        editor?.apply()
    }

    fun readPlayerJson(): Player {
        val gson = Gson()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        var player = Player("Me")

        val jsonMainPLayer: String? = sharedPref?.getString("mainPlayer", "defaultValue")

        if (jsonMainPLayer != "defaultValue") {
            player = gson.fromJson(jsonMainPLayer, Player::class.java)
        }

        return player
    }

    fun readOpponentsJson(): ArrayList<Player> {
        val gson = Gson()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        var opponents = arrayListOf<Player>()

        val jsonOpponents: String? = sharedPref?.getString("opponents", "defaultValue")

        val itemType = object : TypeToken<ArrayList<Player>>() {}.type

        if (jsonOpponents != "defaultValue") {
            opponents = gson.fromJson(jsonOpponents, itemType)
        }

        return opponents
    }

    fun getMatches(): ArrayList<Game> { //This will be json read
        return mainPlayer.games
    }

    fun addMatchMainPlayer(game: Game) {
        mainPlayer.addGame(game)
    }

    fun addMatchOpponent(name: String, game: Game) {
        val opponent: Player? = opponents.find { it.name == name }
        opponent!!.addGame(game)
    }

    fun addOpponent(opponent: Player) {
        opponents.add(opponent)
    }

    fun getPossiblePlayersNames(): ArrayList<String> {
        val possibleOpponents = arrayListOf<String>()
        for (opponent in opponents) {
            possibleOpponents.add(opponent.name)
        }
        return possibleOpponents
    }

    fun getPossibleMainPlayerDecks(): ArrayList<String> {
        val possibleDecks = arrayListOf<String>()
        for (mainDeck in mainPlayer.decks) {
            possibleDecks.add(mainDeck.name)
        }
        return possibleDecks
    }

    fun getPossibleOpponentsDecks(): ArrayList<String> {
        val possibleDecks = arrayListOf<String>()
        opponents.forEach { it.decks.forEach { deck -> possibleDecks.add(deck.name) } }
        return possibleDecks
    }

    fun opponentHasDeck(playerName: String, deckName: String): Boolean {
        val hasDeck = opponents.find { it.name == playerName }?.decks?.find { it.name == deckName }
        return hasDeck != null
    }

    fun mainPlayerHasDeck(deckName: String): Boolean {
        val hasDeck = mainPlayer.decks.find { it.name == deckName }
        return hasDeck != null
    }

    fun getOpponentsDecks(): ArrayList<ArrayList<Deck>> {
        val opponentDecks: ArrayList<ArrayList<Deck>> = arrayListOf()
        for (opponent in opponents) {
            opponentDecks.add(opponent.decks)
        }
        return opponentDecks
    }

    fun getMainPlayerStats(): Stat {
        var matchWinrate: Float = 0F
        var matchesWon: Int = 0
        var matchesLost: Int = 0
        var gameWinrate: Float = 0F
        var gamesWon: Int = 0
        var gamesLost: Int = 0

        for (matches in mainPlayer.games){
            if (matches.game_score.first > matches.game_score.second){
                matchesWon += 1
            } else {
                matchesLost += 1
            }
            gamesWon += matches.game_score.first
            gamesLost +=  matches.game_score.second
        }

        matchWinrate = matchesWon.toFloat() / (matchesWon + matchesLost)
        gameWinrate = gamesWon.toFloat() / (gamesWon + gamesLost)

        return Stat(matchWinrate * 100, matchesWon, matchesLost, gameWinrate * 100, gamesWon, gamesLost)
    }

    fun getMainPlayerDeckStats(name: String): Stat {
        var matchWinrate: Float = 0F
        var matchesWon: Int = 0
        var matchesLost: Int = 0
        var gameWinrate: Float = 0F
        var gamesWon: Int = 0
        var gamesLost: Int = 0

        val games = mainPlayer.games.filter { it.deck_one.name == name }

        for (matches in games){
            if (matches.game_score.first > matches.game_score.second){
                matchesWon += 1
            } else {
                matchesLost += 1
            }
            gamesWon += matches.game_score.first
            gamesLost +=  matches.game_score.second
        }

        matchWinrate = matchesWon.toFloat() / (matchesWon + matchesLost)
        gameWinrate = gamesWon.toFloat() / (gamesWon + gamesLost)

        if (matchWinrate == 1f) matchWinrate = 0f
        if (gameWinrate == 1f) gameWinrate = 0f

        return Stat(matchWinrate * 100, matchesWon, matchesLost, gameWinrate * 100, gamesWon, gamesLost)

    }
}

