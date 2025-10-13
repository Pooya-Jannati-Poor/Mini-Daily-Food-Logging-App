package ir.pooyadev.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.pooyadev.domain.model.local.FoodLog
import ir.pooyadev.presentation.R
import ir.pooyadev.presentation.common.FoodLogClickType
import ir.pooyadev.presentation.common.toPersianTimeString
import ir.pooyadev.presentation.databinding.LayoutRecFoodLogBinding

class AdapterRecTodayFoodLog(private val onFoodLogClicked: (clickType: FoodLogClickType) -> Unit) :
    ListAdapter<FoodLog, AdapterRecTodayFoodLog.FoodLogViewHolder>(FoodLogDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodLogViewHolder {
        val binding = LayoutRecFoodLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodLogViewHolder(private val binding: LayoutRecFoodLogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodLog: FoodLog) {
            binding.tvFoodLogTime.text = foodLog.createdAt.toPersianTimeString()
            binding.tvFoodName.text = binding.root.resources.getString(R.string.food_name, foodLog.foodTitle)
            val calories = foodLog.amount * 10
            binding.tvFoodAmount.text = binding.root.resources.getString(R.string.food_calorie, calories)
            binding.imgDeleteFoodLog.setOnClickListener {
                onFoodLogClicked(FoodLogClickType.DeleteFoodLog(foodLog))
            }
            binding.root.setOnClickListener {
                onFoodLogClicked(FoodLogClickType.EditFoodLog(foodLog))
            }
        }
    }

    class FoodLogDiffCallback : DiffUtil.ItemCallback<FoodLog>() {
        override fun areItemsTheSame(oldItem: FoodLog, newItem: FoodLog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FoodLog, newItem: FoodLog): Boolean {
            return oldItem == newItem
        }
    }
}