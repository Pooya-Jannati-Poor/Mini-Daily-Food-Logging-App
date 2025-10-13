package ir.pooyadev.presentation.model

import ir.pooyadev.domain.model.local.FoodLog

data class TodayFoodLogUiState(
    val foodLogs: List<FoodLog> = emptyList(),
    val totalCalories: Int = 0,
    val isLoading: Boolean = true
)
