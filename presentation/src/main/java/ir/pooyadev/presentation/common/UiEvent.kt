package ir.pooyadev.presentation.common

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent

    object Success : UiEvent

    data object ShowUndoSnackbar : UiEvent
}