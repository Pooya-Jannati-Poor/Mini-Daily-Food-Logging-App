package ir.pooyadev.data.di.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.pooyadev.domain.repository.local.FoodLogRepository
import ir.pooyadev.domain.usecases.local.CalculateCaloriesUseCase
import ir.pooyadev.domain.usecases.local.DeleteFoodLogUseCase
import ir.pooyadev.domain.usecases.local.UpsertFoodLogUseCase

@Module
@InstallIn(SingletonComponent::class)
object FoodLoggingUseCasesModule {

    @Provides
    fun provideUpsertFoodLoggingUseCase(foodLoggingRepository: FoodLogRepository): UpsertFoodLogUseCase {
        return UpsertFoodLogUseCase(foodLoggingRepository)
    }

    @Provides
    fun provideDeleteFoodLoggingUseCase(foodLoggingRepository: FoodLogRepository): DeleteFoodLogUseCase {
        return DeleteFoodLogUseCase(foodLoggingRepository)
    }

    @Provides
    fun provideCalculateCaloriesUseCase(): CalculateCaloriesUseCase {
        return CalculateCaloriesUseCase()
    }

}