package com.example.kotlin.data

data class Player(val name: String, val games: ArrayList<Game>) {

    fun addGame(game: Game){ games.add(game)}

}
