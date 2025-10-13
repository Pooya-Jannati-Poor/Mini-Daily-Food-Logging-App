package ir.pooyadev.domain.usecases.local

import ir.pooyadev.domain.common.getCurrentEpochDayInTehran
import ir.pooyadev.domain.model.local.FoodLog
import ir.pooyadev.domain.repository.local.FoodLogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchFoodLogsForDayUseCase @Inject constructor(private val foodLogRepository: FoodLogRepository) {
    operator fun invoke(): Flow<List<FoodLog>> {
        val todayEpochDay = getCurrentEpochDayInTehran()
        return foodLogRepository.fetchFoodLogsForDay(todayEpochDay)
    }
}