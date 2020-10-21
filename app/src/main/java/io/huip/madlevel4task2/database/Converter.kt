package io.huip.madlevel4task2.database

import androidx.room.TypeConverter
import java.util.*
import io.huip.madlevel4task2.model.Move
import io.huip.madlevel4task2.model.Result

/**
 * Converts game data to storable types.
 */
class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromMove(value: String?): Move? {
        return value?.let { Move.valueOf(it) }
    }

    @TypeConverter
    fun fromResult(value: String?): Result? {
        return value?.let { Result.valueOf(it) }
    }

    @TypeConverter
    fun moveToString(Move: Move?): String? {
        return Move?.toString()
    }

    @TypeConverter
    fun resultToString(result: Result?): String? {
        return result?.toString()
    }
}