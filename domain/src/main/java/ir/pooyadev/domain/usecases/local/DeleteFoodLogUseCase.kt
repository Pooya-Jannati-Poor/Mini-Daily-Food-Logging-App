package ir.pooyadev.domain.usecases.local

import ir.pooyadev.domain.model.local.FoodLog
import ir.pooyadev.domain.repository.local.FoodLogRepository
import javax.inject.Inject

class DeleteFoodLogUseCase @Inject constructor(private val foodLogRepository: FoodLogRepository) {
    suspend operator fun invoke(foodLog: FoodLog): Int {
        return foodLogRepository.deleteFoodLog(foodLog)
    }
}