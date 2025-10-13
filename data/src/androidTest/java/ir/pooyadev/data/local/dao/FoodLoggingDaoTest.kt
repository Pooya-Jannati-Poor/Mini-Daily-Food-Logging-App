package ir.pooyadev.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.pooyadev.data.local.AppDatabase // نام کلاس دیتابیس خود را جایگزین کنید
import ir.pooyadev.data.local.entities.FoodLogEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodLoggingDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: FoodLoggingDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.foodLoggingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun upsertFoodLog_and_fetchForDay_returnsCorrectItem() = runTest {
        // Arrange
        val epochDayForTest = 19278L
        val foodLog = FoodLogEntity(
            id = "uuid-1",
            epochDay = epochDayForTest,
            foodTitle = "سیب",
            unit = "g",
            amount = 125,
            createdAt = System.currentTimeMillis()
        )

        // Act
        dao.upsertFoodLog(foodLog)

        val logsForDay = dao.fetchFoodLogsForDay(epochDayForTest).first()

        // Assert
        assertEquals(1, logsForDay.size)
        assertEquals(foodLog.id, logsForDay[0].id)
        assertEquals("سیب", logsForDay[0].foodTitle)
    }

    @Test
    fun deleteFoodLog_removesItemFromDatabase() = runTest {
        // Arrange
        val epochDayForTest = 19278L
        val foodLog = FoodLogEntity(id = "uuid-1", epochDay = epochDayForTest, foodTitle = "سیب", unit = "g", amount = 250, createdAt = 10000L)
        dao.upsertFoodLog(foodLog)

        // Act
        dao.deleteFoodLog(foodLog)
        val logsForDay = dao.fetchFoodLogsForDay(epochDayForTest).first()

        // Assert
        assertTrue(logsForDay.isEmpty())
    }

    @Test
    fun fetchFoodLogsForDay_returnsOnlyItemsForThatDay() = runTest {
        // Arrange
        val day1 = 19278L
        val day2 = 19279L
        val foodLogDay1 = FoodLogEntity(id = "uuid-1", epochDay = day1, foodTitle = "سیب", unit = "g", amount = 160, createdAt = 100000L)
        val foodLogDay2 = FoodLogEntity(id = "uuid-2", epochDay = day2, foodTitle = "موز", unit = "g", amount = 240, createdAt = 200000L)

        dao.upsertFoodLog(foodLogDay1)
        dao.upsertFoodLog(foodLogDay2)

        // Act
        val logsForDay1 = dao.fetchFoodLogsForDay(day1).first()

        // Assert
        assertEquals(1, logsForDay1.size)
        assertEquals("uuid-1", logsForDay1[0].id)
    }
}