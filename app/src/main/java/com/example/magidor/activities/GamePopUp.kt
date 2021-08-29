package com.example.magidor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.magidor.R

class GamePopUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.game_add_pop_up)
    }
}