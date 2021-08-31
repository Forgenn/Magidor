package com.example.magidor.data

data class Game(val game_score: Pair<Int, Int>, val deck_one: Deck, val deck_two: Deck, val player_play: Boolean)
