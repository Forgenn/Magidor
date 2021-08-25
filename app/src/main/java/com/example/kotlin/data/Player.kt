package com.example.kotlin.data

data class Player(val name: String = "", val games: ArrayList<Game> = arrayListOf()) {

    fun addGame(game: Game){ games.add(game)}

}
