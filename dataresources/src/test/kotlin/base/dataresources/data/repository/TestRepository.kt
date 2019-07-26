package base.dataresources.data.repository

import base.dataresources.data.db.TestClassDao
import com.github.icarohs7.unoxandroidarch.Injector
import org.koin.core.get

class TestRepository : TestClassDao by Injector.get()