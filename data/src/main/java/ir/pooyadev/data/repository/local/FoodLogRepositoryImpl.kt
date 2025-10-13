package ir.pooyadev.data.repository.local

import ir.pooyadev.data.local.dao.FoodLoggingDao
import ir.pooyadev.data.local.entities.FoodLogEntity
import ir.pooyadev.domain.model.local.FoodLog
import ir.pooyadev.domain.repository.local.FoodLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FoodLogRepositoryImpl @Inject constructor(
    private val foodLoggingDao: FoodLoggingDao,
) : FoodLogRepository {

    override suspend fun upsertFoodLog(foodLog: FoodLog): Long {
        val foodLogEntity = FoodLogEntity.fromDomain(foodLog)
        return foodLoggingDao.upsertFoodLog(foodLogEntity)
    }

    override fun fetchFoodLogsForDay(epochDay: Long): Flow<List<FoodLog>> {
        return foodLoggingDao.fetchFoodLogsForDay(epochDay).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun deleteFoodLog(foodLog: FoodLog): Int {
        val foodLogEntity = FoodLogEntity.fromDomain(foodLog)
        return foodLoggingDao.deleteFoodLog(foodLogEntity)
    }

}