package ir.pooyadev.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.pooyadev.presentation.R
import ir.pooyadev.presentation.common.UiEvent
import ir.pooyadev.presentation.databinding.LayoutDialogFoodLogBinding
import ir.pooyadev.presentation.viewmodel.TodayFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditFoodLogBottomSheetFragment : BottomSheetDialogFragment() {
    private val viewModel: TodayFragmentViewModel by viewModels()
    private var _binding: LayoutDialogFoodLogBinding? = null
    private val binding get() = _binding!!

    private val args: AddEditFoodLogBottomSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LayoutDialogFoodLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkViewsForEditMode()

        setupClickListeners()
        observeEvents()
    }

    private fun checkViewsForEditMode() {
        if (args.isEditFoodLog) {
            binding.tvBottomSheetTitle.text = resources.getString(R.string.edit_food_log)
            binding.etFoodName.setText(args.foodLogForEdit?.foodTitle)
            binding.etFoodUnit.setText(args.foodLogForEdit?.unit)
            binding.etFoodAmount.setText(args.foodLogForEdit?.amount.toString())
        } else {
            binding.tvBottomSheetTitle.text = resources.getString(R.string.add_food_log)
        }
    }


    private fun setupClickListeners() {
        binding.btnSubmit.setOnClickListener {
            viewModel.upsertFoodLog(
                foodLogId = args.foodLogForEdit?.id,
                name = binding.etFoodName.text.toString(),
                unit = binding.etFoodUnit.text.toString(),
                amountString = binding.etFoodAmount.text.toString(),
                createAt = args.foodLogForEdit?.createdAt,
                epochDay = args.foodLogForEdit?.epochDay
            )
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is UiEvent.ShowToast -> {
                            Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is UiEvent.Success -> {
                            dismiss()
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}