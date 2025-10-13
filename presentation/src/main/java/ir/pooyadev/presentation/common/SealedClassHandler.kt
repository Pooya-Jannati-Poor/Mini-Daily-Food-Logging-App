package ir.pooyadev.presentation.common

import ir.pooyadev.domain.model.local.FoodLog

sealed class FoodLogClickType {
    data class DeleteFoodLog(val foodLog: FoodLog) : FoodLogClickType()
    data class EditFoodLog(val foodLog: FoodLog) : FoodLogClickType()
}