package ir.pooyadev.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pooyadev.domain.model.local.FoodLog
import ir.pooyadev.domain.usecases.local.CalculateCaloriesUseCase
import ir.pooyadev.domain.usecases.local.DeleteFoodLogUseCase
import ir.pooyadev.domain.usecases.local.FetchFoodLogsForDayUseCase
import ir.pooyadev.domain.usecases.local.UpsertFoodLogUseCase
import ir.pooyadev.presentation.common.UiEvent
import ir.pooyadev.presentation.model.TodayFoodLogUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayFragmentViewModel @Inject constructor(
    application: Application,
    private val upsertFoodLogUseCase: UpsertFoodLogUseCase,
    private val fetchFoodLogsForDayUseCase: FetchFoodLogsForDayUseCase,
    private val deleteFoodLogUseCase: DeleteFoodLogUseCase,
    private val calculateCaloriesUseCase: CalculateCaloriesUseCase,
) :
    AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(TodayFoodLogUiState())
    val uiState: StateFlow<TodayFoodLogUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var lastDeletedFoodLog: FoodLog? = null

    init {
        loadFoodLogs()
    }

    private fun loadFoodLogs() {
        fetchFoodLogsForDayUseCase().onEach { foodLogs ->
            val totalCalories = foodLogs.sumOf { calculateCaloriesUseCase(it.amount) }
            _uiState.value = _uiState.value.copy(
                foodLogs = foodLogs,
                totalCalories = totalCalories,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }

    fun upsertFoodLog(
        foodLogId: String? = null,
        name: String,
        unit: String,
        amountString: String,
        createAt: Long? = null,
        epochDay: Long? = null,
    ) {
        viewModelScope.launch {
            val amount = amountString.toIntOrNull() ?: 0
            try {
                upsertFoodLogUseCase(
                    foodLogId = foodLogId,
                    foodName = name,
                    unit = unit,
                    amount = amount,
                    createAt = createAt,
                    epochDay = epochDay
                )
                _eventFlow.emit(UiEvent.ShowToast("با موفقیت ذخیره شد!"))
                _eventFlow.emit(UiEvent.Success)
            } catch (e: IllegalArgumentException) {
                _eventFlow.emit(UiEvent.ShowToast(e.message ?: "خطای نامشخص در ورودی‌ها"))
            }

        }
    }

    fun deleteFoodLog(foodLog: FoodLog) {
        viewModelScope.launch {
            lastDeletedFoodLog = foodLog
            deleteFoodLogUseCase(foodLog)
            _eventFlow.emit(UiEvent.ShowUndoSnackbar)
        }
    }

    fun onUndoDeleteClicked() {
        viewModelScope.launch {
            lastDeletedFoodLog?.let {
                upsertFoodLog(
                    foodLogId = it.id,
                    name = it.foodTitle,
                    unit = it.unit,
                    amountString = it.amount.toString(),
                    createAt = it.createdAt,
                    epochDay = it.epochDay
                )
                lastDeletedFoodLog = null
            }
        }
    }

}