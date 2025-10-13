package ir.pooyadev.presentation.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.pooyadev.domain.model.local.FoodLog
import ir.pooyadev.presentation.R
import ir.pooyadev.presentation.base.BaseFragment
import ir.pooyadev.presentation.common.FoodLogClickType
import ir.pooyadev.presentation.common.UiEvent
import ir.pooyadev.presentation.common.getTodayShamsiDate
import ir.pooyadev.presentation.databinding.FragmentTodayBinding
import ir.pooyadev.presentation.view.adapter.AdapterRecTodayFoodLog
import ir.pooyadev.presentation.viewmodel.TodayFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue


@AndroidEntryPoint
class TodayFragment : BaseFragment<FragmentTodayBinding>(FragmentTodayBinding::inflate) {

    private val viewModel: TodayFragmentViewModel by viewModels()

    private lateinit var foodLogAdapter: AdapterRecTodayFoodLog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTodayDate()

        setupRecyclerView()

        observeFoodLogViewModel()

        setupAddFoodLogFab()

        saveFoodLogEventController()

    }

    private fun setTodayDate() {
        binding.tvTodayDate.text = getTodayShamsiDate()
    }

    private fun setupRecyclerView() {
        foodLogAdapter = AdapterRecTodayFoodLog { clickType ->
            when (clickType) {
                is FoodLogClickType.DeleteFoodLog -> {
                    deleteFoodLog(clickType.foodLog)
                }

                is FoodLogClickType.EditFoodLog -> {
                    openBottomSheetFoodLog(true, clickType.foodLog)
                }
            }
        }
        binding.recTodayData.apply {
            adapter = foodLogAdapter
            layoutManager = LinearLayoutManager(fragmentContext)
        }
    }

    private fun observeFoodLogViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    updateTodayFoodLogsList(state.foodLogs)
                    binding.tvTodayTotalCalories.text =
                        resources.getString(R.string.today_total_calories, state.totalCalories)
                }
            }
        }
    }

    private fun updateTodayFoodLogsList(foodLog: List<FoodLog> = emptyList()) {

        binding.apply {
            if (foodLog.isEmpty()) {
                tvEmptyList.visibility = View.VISIBLE
                recTodayData.visibility = View.GONE
            } else {
                tvEmptyList.visibility = View.GONE
                recTodayData.visibility = View.VISIBLE
            }

        }

        foodLogAdapter.submitList(foodLog)
    }

    private fun deleteFoodLog(foodLog: FoodLog) {
        viewModel.deleteFoodLog(foodLog)
    }

    private fun setupAddFoodLogFab() {
        binding.fabOpenBottomSheetFoodLog.setOnClickListener {
            openBottomSheetFoodLog(false)
        }
    }

    private fun openBottomSheetFoodLog(isEditFoodLog: Boolean, foodLog: FoodLog? = null) {

        val action =
            TodayFragmentDirections.actionTodayFragmentToAddEditFoodLogBottomSheetFragment(
                isEditFoodLog,
                foodLog
            )
        findNavController().navigate(action)

    }

    private fun saveFoodLogEventController() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is UiEvent.ShowToast -> {
                            Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is UiEvent.ShowUndoSnackbar -> {
                            showUndoDeleteSnackbar()
                        }
                        else -> {

                        }
                    }
                }
            }
        }

    }

    private fun showUndoDeleteSnackbar() {
        Snackbar.make(requireView(), "Food Log Deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                viewModel.onUndoDeleteClicked()
            }
            .show()
    }

}