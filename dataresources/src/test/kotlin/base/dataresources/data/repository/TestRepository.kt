package base.dataresources.data.repository

import base.dataresources.data.db.TestClassDao
import base.coreresources.Injector
import org.koin.core.get

class TestRepository : TestClassDao by Injector.get()