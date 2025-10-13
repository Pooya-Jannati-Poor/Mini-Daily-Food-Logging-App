package ir.pooyadev.domain.usecases.local

import javax.inject.Inject

class CalculateCaloriesUseCase @Inject constructor() {
    operator fun invoke(amount: Int): Int {
        if (amount < 0) {
            return 0
        }
        return amount * 10
    }
}