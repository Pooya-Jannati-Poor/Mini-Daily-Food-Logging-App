package ir.pooyadev.domain.usecase

import ir.pooyadev.domain.usecases.local.CalculateCaloriesUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateCaloriesUseCaseTest {

    private lateinit var calculateCaloriesUseCase: CalculateCaloriesUseCase

    @Before
    fun setUp() {
        calculateCaloriesUseCase = CalculateCaloriesUseCase()
    }

    @Test
    fun `calculate calories with positive amount, returns correct result`() {
        // Arrange
        val amount = 15
        val expectedCalories = 150

        // Act
        val result = calculateCaloriesUseCase(amount)

        // Assert
        assertEquals(expectedCalories, result)
    }

    @Test
    fun `calculate calories with zero amount, returns zero`() {
        // Arrange
        val amount = 0
        val expectedCalories = 0

        // Act
        val result = calculateCaloriesUseCase(amount)

        // Assert
        assertEquals(expectedCalories, result)
    }

    @Test
    fun `calculate calories with large amount, returns correct result`() {
        // Arrange
        val amount = 100_000
        val expectedCalories = 1_000_000

        // Act
        val result = calculateCaloriesUseCase(amount)

        // Assert
        assertEquals(expectedCalories, result)
    }

    @Test
    fun `calculate calories with negative amount, returns zero`() {
        // Arrange
        val amount = -50
        val expectedCalories = 0

        // Act
        val result = calculateCaloriesUseCase(amount)

        // Assert
        assertEquals(expectedCalories, result)
    }
}