package base.dataresources.data

import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import base.dataresources.data.db.TestDatabase
import base.dataresources.data.entities.TestClass
import base.dataresources.data.repository.TestRepository
import com.github.icarohs7.unoxandroidarch.Injector
import io.reactivex.subscribers.TestSubscriber
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.get
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldContain
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotContain

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O])
class DBAndRepositoryTest {
    private val testRepository by lazy { Injector.get<TestRepository>() }

    @Before
    fun setup() {
        val database = Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), TestDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        stopKoin()
        startKoin {
            modules(module {
                single { database }
                single { database.testDao() }
                single { TestRepository() }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should insert items`() {
        insertMockData()
        assertStoredItemCount(3)
        assertItemsStored(TestClass(1, "Omai wa mou shindeiru!"))
        assertItemsStored(TestClass(2, "NANI!?"))
        assertItemsStored(TestClass(3, "ZA WARUDO!"))
    }

    @Test
    fun `should update items`() {
        insertMockData()
        val oldobj1 = TestClass(1, "Omai wa mou shindeiru!")
        val newobj1 = TestClass(1, "MUDA MUDA MUDA MUDA MUDA!!!!")

        assertItemsStored(oldobj1)
        assertItemsNotStored(newobj1)
        runBlocking { testRepository.update(newobj1) }
        assertItemsStored(newobj1)
        assertItemsNotStored(oldobj1)

        val oldobj2 = TestClass(2, "NANI!?")
        val oldobj3 = TestClass(3, "ZA WARUDO!")
        val newobj2 = TestClass(2, "Sasuga Ainz-sama")
        val newobj3 = TestClass(3, "Chika to Chika to Chika Chika")

        assertItemsStored(oldobj2)
        assertItemsNotStored(newobj2)
        assertItemsStored(oldobj3)
        assertItemsNotStored(newobj3)
        runBlocking { testRepository.updateAll(listOf(newobj2, newobj3)) }
        assertItemsStored(newobj2)
        assertItemsNotStored(oldobj2)
        assertItemsStored(newobj3)
        assertItemsNotStored(oldobj3)
    }

    @Test
    fun `should delete items`() {
        insertMockData()

        val oldobj1 = TestClass(1, "Omai wa mou shindeiru!")
        val oldobj2 = TestClass(2, "NANI!?")
        val oldobj3 = TestClass(3, "ZA WARUDO!")

        assertStoredItemCount(3)
        assertItemsStored(oldobj1)
        runBlocking { testRepository.delete(oldobj1) }
        assertStoredItemCount(2)
        assertItemsNotStored(oldobj1)

        assertItemsStored(oldobj2, oldobj3)
        runBlocking { testRepository.deleteAll(listOf(oldobj2, oldobj3)) }
        assertStoredItemCount(0)
        assertItemsNotStored(oldobj2, oldobj3)
    }

    @Test
    fun `should erase the table`() {
        insertMockData()
        assertStoredItemCount(3)
        runBlocking { testRepository.eraseTable() }
        assertStoredItemCount(0)
    }

    @Test
    fun `should get all items`() {
        insertMockData()
        assertStoredItemCount(3)
        val all = runBlocking { testRepository.getAll() }
        val e1 = TestClass(1, "Omai wa mou shindeiru!")
        val e2 = TestClass(2, "NANI!?")
        val e3 = TestClass(3, "ZA WARUDO!")
        all shouldEqual listOf(e1, e2, e3)
    }

    private fun insertMockData() {
        assertStoredItemCount(0)
        runBlocking { testRepository.insert(TestClass(1, "Omai wa mou shindeiru!")) }
        runBlocking { testRepository.insertAll(listOf(TestClass(2, "NANI!?"), TestClass(3, "ZA WARUDO!"))) }
    }

    private fun assertItemsStored(vararg items: TestClass) {
        val stored = runBlocking { testRepository.getAll() }
        items.forEach { stored shouldContain it }
    }

    private fun assertItemsNotStored(vararg items: TestClass) {
        val stored = runBlocking { testRepository.getAll() }
        items.forEach { stored shouldNotContain it }
    }

    private fun assertStoredItemCount(count: Int) {
        val stored = runBlocking { testRepository.getAll() }
        stored.size shouldEqual count
    }
}