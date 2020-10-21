package io.huip.madlevel4task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.huip.madlevel4task2.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class GameListRoomDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAMES_LIST_DATABASE"

        @Volatile
        private var GameListRoomDatabaseInstance: GameListRoomDatabase? = null

        fun getDatabase(context: Context): GameListRoomDatabase? {
            if (GameListRoomDatabaseInstance == null) {
                synchronized(GameListRoomDatabase::class.java) {
                    if (GameListRoomDatabaseInstance == null) {
                        GameListRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                GameListRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return GameListRoomDatabaseInstance
        }
    }
}