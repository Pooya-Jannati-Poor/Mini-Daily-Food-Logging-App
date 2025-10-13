package ir.pooyadev.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.pooyadev.domain.model.local.FoodLog
import java.util.UUID

@Entity(tableName = "food_logs_table")
data class FoodLogEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val epochDay: Long,
    val foodTitle: String,
    val unit: String,
    val amount: Int,
    val createdAt: Long,
) {
    fun toDomain(): FoodLog {
        return FoodLog(
            id = this.id,
            epochDay = this.epochDay,
            foodTitle = this.foodTitle,
            unit = this.unit,
            amount = this.amount,
            createdAt = this.createdAt
        )
    }

    companion object {
        fun fromDomain(foodLog: FoodLog): FoodLogEntity {
            return FoodLogEntity(
                id = foodLog.id,
                epochDay = foodLog.epochDay,
                foodTitle = foodLog.foodTitle,
                unit = foodLog.unit,
                amount = foodLog.amount,
                createdAt = foodLog.createdAt,
            )
        }
    }
}
