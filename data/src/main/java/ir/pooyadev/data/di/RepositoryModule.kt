package ir.pooyadev.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.pooyadev.data.repository.local.FoodLogRepositoryImpl
import ir.pooyadev.domain.repository.local.FoodLogRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodLogRepository(
        impl: FoodLogRepositoryImpl
    ): FoodLogRepository

}