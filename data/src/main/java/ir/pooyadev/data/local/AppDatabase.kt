package ir.pooyadev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.pooyadev.data.local.dao.FoodLoggingDao
import ir.pooyadev.data.local.entities.FoodLogEntity

@Database(
    entities = [FoodLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodLoggingDao(): FoodLoggingDao
}