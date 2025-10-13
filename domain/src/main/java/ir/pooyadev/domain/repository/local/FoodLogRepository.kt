package ir.pooyadev.domain.repository.local

import ir.pooyadev.domain.model.local.FoodLog
import kotlinx.coroutines.flow.Flow

interface FoodLogRepository {

    suspend fun upsertFoodLog(foodLog: FoodLog): Long

    fun fetchFoodLogsForDay(epochDay: Long): Flow<List<FoodLog>>

    suspend fun deleteFoodLog(foodLog: FoodLog): Int

}