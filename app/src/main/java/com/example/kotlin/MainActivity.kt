package com.example.kotlin

//import com.example.kotlin.fragments.GamesFragment
import android.os.Bundle
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.fragments.ItemFragment
import com.example.kotlin.Adapters.ViewPagerAdapter
import com.example.kotlin.data.Player
import com.example.kotlin.fragments.Players
import kotlinx.android.synthetic.main.activity_main.*

var popupWindow : PopupWindow = PopupWindow()
var mainPlayer: Player = Player()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTabs()
    }

    /**
     * Function is used to get the Items List which is added in the list.
     */
    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ItemFragment(), "Matches")
        adapter.addFragment(Players(), "Players")
        adapter.addFragment(Players(), "Stats")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

}