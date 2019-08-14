package base.dataresources.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import base.dataresources.data.entities.TestClass
import kotlinx.coroutines.flow.Flow

@Dao
interface TestClassDao : BaseDao<TestClass> {
    @Query("DELETE FROM `TestClass`")
    override suspend fun eraseTable()

    @Query("SELECT * FROM `TestClass`")
    suspend fun getAll(): List<TestClass>

    @Query("SELECT * FROM `TestClass` WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TestClass?

    @Query("SELECT * FROM `TestClass`")
    override fun liveData(): LiveData<List<TestClass>>

    @Query("SELECT * FROM `TestClass`")
    override fun flow(): Flow<List<TestClass>>
}