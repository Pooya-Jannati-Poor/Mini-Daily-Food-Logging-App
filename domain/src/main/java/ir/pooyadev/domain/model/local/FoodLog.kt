package ir.pooyadev.domain.model.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FoodLog(
    val id: String,
    val epochDay: Long,
    val foodTitle: String,
    val unit: String,
    val amount: Int,
    val createdAt: Long,
): Parcelable
