package io.huip.madlevel4task2.model

import androidx.room.*
import java.util.*

@Entity(tableName = "games")
data class Game (
    @ColumnInfo(name = "results")
    var result: Result,

    @ColumnInfo(name = "playerMoves")
    var playerMove: Move,

    @ColumnInfo(name = "computerMoves")
    var computerMove: Move,

    @ColumnInfo(name = "date")
    var date: Date = Date(),

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)

//TODO: figure out if making seperate enum classes is neater!
enum class Move {
    ROCK,
    PAPER,
    SCISSORS // one scissor, a pair of scissors.
}

enum class Result {
    WIN,
    LOSE,
    DRAW
}