package ir.pooyadev.domain.usecases.local

import ir.pooyadev.domain.common.getCurrentEpochDayInTehran
import ir.pooyadev.domain.model.local.FoodLog
import ir.pooyadev.domain.repository.local.FoodLogRepository
import java.util.UUID
import javax.inject.Inject

class UpsertFoodLogUseCase @Inject constructor(private val foodLogRepository: FoodLogRepository) {
    suspend operator fun invoke(
        foodLogId: String? = null,
        foodName: String,
        unit: String,
        amount: Int,
        createAt: Long? = null,
        epochDay: Long? = null,
    ): Long {

        if (foodName.isBlank()) {
            throw IllegalArgumentException("نام غذا نمی‌تواند خالی باشد.")
        }
        if (unit.isBlank()) {
            throw IllegalArgumentException("واحد نمی‌تواند خالی باشد.")
        }
        if (amount <= 0) {
            throw IllegalArgumentException("مقدار باید بیشتر از صفر باشد.")
        }

        val id = if (foodLogId.isNullOrBlank()) UUID.randomUUID().toString() else foodLogId
        val createdAt = createAt ?: System.currentTimeMillis()
        val epochDay = epochDay ?: getCurrentEpochDayInTehran()

        val foodLog = FoodLog(
            id = id,
            epochDay = epochDay,
            foodTitle = foodName,
            unit = unit,
            amount = amount,
            createdAt = createdAt
        )
        return foodLogRepository.upsertFoodLog(foodLog)
    }
}