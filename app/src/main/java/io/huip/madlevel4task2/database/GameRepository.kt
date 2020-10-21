package io.huip.madlevel4task2.database

import android.content.Context
import io.huip.madlevel4task2.model.Game

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database =
            GameListRoomDatabase.getDatabase(
                context
            )
        gameDao = database!!.gameDao()
    }

    suspend fun getGames(): List<Game> = gameDao.getAllGames()

    suspend fun insertGame(game: Game) = gameDao.insertGame(game)

    suspend fun deleteGames() = gameDao.deleteAllGames()

    suspend fun getWins() = gameDao.getWins()

    suspend fun getLosses() = gameDao.getLosses()

    suspend fun getDraws() = gameDao.getDraws()
}