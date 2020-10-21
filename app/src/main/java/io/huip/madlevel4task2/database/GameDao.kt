package io.huip.madlevel4task2.database

import androidx.room.*
import io.huip.madlevel4task2.model.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(results) FROM games WHERE results = 'WIN'")
    suspend fun getWins(): Int

    @Query("SELECT COUNT(results) FROM games WHERE results = 'LOSE'")
    suspend fun getLosses(): Int

    @Query("SELECT COUNT(results) FROM games WHERE results = 'DRAW'")
    suspend fun getDraws(): Int
}