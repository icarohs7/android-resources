package base.dataresources.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import base.dataresources.data.entities.TestClass

@Database(entities = [TestClass::class], version = 1)
abstract class TestDatabase : RoomDatabase() {
    abstract fun testDao(): TestClassDao
}