package com.example.magidor.activities

//import com.example.kotlin.fragments.GamesFragment
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.magidor.fragments.Match
import com.example.magidor.Adapters.ViewPagerAdapter
import com.example.magidor.R
import com.example.magidor.data.Game
import com.example.magidor.data.Player
import com.example.magidor.fragments.Players
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array


class MainActivity : AppCompatActivity() {

    var mainPlayer: Player = Player()
    var opponents = arrayListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPlayer = readPlayerJson()

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
        var gson = Gson()
        var jsonString = gson.toJson(mainPlayer)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        var editor = sharedPref?.edit()
        editor?.putString("mainPlayer", jsonString)
        editor?.apply()
    }

    fun readPlayerJson(): Player{
        val gson = Gson()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        var player = Player("MainPlayer")

        val jsonString: String? = sharedPref?.getString("mainPlayer", "defaultValue")
        if (jsonString != "defaultValue"){
            player = gson.fromJson(jsonString, Player::class.java)
        }

        return player
    }

     fun getMatches(): ArrayList<Game> { //This will be json read
        return mainPlayer.games
    }



}

