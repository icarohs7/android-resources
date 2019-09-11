package base.corextresources.presentation

import androidx.appcompat.app.AppCompatActivity
import base.coreresources.extensions.startActivity
import base.coreresources.toplevel.onActivity
import kotlin.reflect.KClass

typealias ActivityClass = KClass<out AppCompatActivity>

object AppView {
    lateinit var SPLASH: ActivityClass
    lateinit var LOGIN: ActivityClass
    lateinit var MAIN: ActivityClass
}

object CoreNavigation {
    fun splashActivity() {
        onActivity { startActivity(AppView.SPLASH, finishActivity = true) }
    }

    fun loginActivity() {
        onActivity { startActivity(AppView.LOGIN, finishActivity = true) }
    }

    fun mainActivity() {
        onActivity { startActivity(AppView.MAIN, finishActivity = true) }
    }
}