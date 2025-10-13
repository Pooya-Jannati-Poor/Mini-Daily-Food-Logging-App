package ir.pooyadev.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ir.pooyadev.data.local.entities.FoodLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodLoggingDao {

    @Upsert
    suspend fun upsertFoodLog(foodLog: FoodLogEntity): Long

    @Query("SELECT * FROM food_logs_table WHERE epochDay = :epochDay ORDER BY createdAt DESC")
    fun fetchFoodLogsForDay(epochDay: Long): Flow<List<FoodLogEntity>>

    @Delete
    suspend fun deleteFoodLog(foodLog: FoodLogEntity): Int

}