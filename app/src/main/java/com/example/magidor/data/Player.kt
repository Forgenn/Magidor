package com.example.magidor.data

data class Player(val name: String = "", val games: ArrayList<Game> = arrayListOf(), val decks: ArrayList<Deck> = arrayListOf()) {

    fun addGame(game: Game){ games.add(game)}

}
