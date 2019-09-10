package base.coreresources.testutils

import android.app.Application
import base.coreresources.R

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_AppCompat)
    }
}