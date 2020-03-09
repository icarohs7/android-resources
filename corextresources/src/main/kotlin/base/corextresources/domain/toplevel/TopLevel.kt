@file:JvmName("TopLevel")

package base.corextresources.domain.toplevel

import android.app.Activity
import android.app.Service
import android.os.Bundle
import android.os.Process
import android.util.Log
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import arrow.core.Try
import base.corextresources.R
import base.corextresources.presentation.main.BaseMainActivity
import com.andrognito.flashbar.Flashbar
import base.coreresources.Injector
import base.coreresources.toplevel.FlashBar
import base.coreresources.toplevel.Intent
import base.coreresources.toplevel.onActivity
import base.corextresources.presentation.main.BaseFragholderMainActivity
import kotlinx.serialization.UpdateMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.core.get
import splitties.init.appCtx
import timber.log.Timber

/**
 * Set the title of the activity
 * currently in foreground
 */
var activityTitle: String
    get() = throw NotImplementedError()
    set(value) {
        onActivity { title = value }
    }

/**
 * Short hand syntax to fetch an instance from
 * the DI container
 */
inline fun <reified T : Any> kget(): T = Injector.get()

/**
 * Helper used to navigate to another
 * navigation destination or action
 */
fun navigate(directions: NavDirections, navOptions: NavOptions? = null) {
    val options = navOptions ?: androidx.navigation.navOptions {
        anim {
            enter = R.anim.enter_animation
            exit = R.anim.exit_animation
            popEnter = R.anim.enter_animation
            popExit = R.anim.exit_animation
        }
    }
    navigateOnMain { navController ->
        Try { navController.navigate(directions, options) }
            .fold(Timber.tag("Navigation")::e) { Timber.tag("Navigation").i("$it") }
    }
}

/**
 * Helper used to navigate to another
 * navigation destination or action
 */
fun navigate(
    dest: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val options = navOptions ?: androidx.navigation.navOptions {
        anim {
            enter = R.anim.enter_animation
            exit = R.anim.exit_animation
            popEnter = R.anim.enter_animation
            popExit = R.anim.exit_animation
        }
    }
    navigateOnMain { navController ->
        Try { navController.navigate(dest, args, options, navigatorExtras) }
            .fold(Timber.tag("Navigation")::e) { Timber.tag("Navigation").i("$it") }
    }
}

/**
 * Block used to execute a navigation function
 * on all available instances of Activities
 * with a navController attached to
 */
private fun navigateOnMain(block: (controller: NavController) -> Unit) {
    onActivity<BaseMainActivity> { block(navController) }
    onActivity<BaseFragholderMainActivity> { block(navController) }
}

/**
 * Navigate to the giving location popping out all backstack
 * destinations in between
 */
fun navigatePopping(@IdRes destination: Int, args: Bundle? = null) {
    val navOptions = NavOptions
        .Builder()
        .setPopUpTo(destination, true)
        .build()
    navigate(destination, args, navOptions)
}

/** Show a flashbar snackbar with a yellow gradient background */
fun showWarningFlashBar(
    message: String = "",
    duration: Int = 1500,
    gravity: Flashbar.Gravity = Flashbar.Gravity.TOP,
    context: Activity? = null
) {
    fun messageBuilder(act: Activity) {
        FlashBar.show(act, message, duration, gravity) { backgroundDrawable(R.drawable.bg_gradient_yellow) }
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/** Show a flashbar snack with the default background and bottom gravity */
fun showFlashSnackbar(message: String, duration: Int = 1000, context: Activity? = null) {
    fun messageBuilder(act: Activity) {
        FlashBar.show(act, message, duration, Flashbar.Gravity.BOTTOM)
    }
    context?.let(::messageBuilder) ?: onActivity(::messageBuilder)
}

/**
 * Log the given value using the logging
 * tag LOGEXECUTION and return the parameter
 */
fun <T> logExecution(value: T): T {
    Timber.tag("LOGEXECUTION").i("$value")
    return value
}

/**
 * Start the given service passing an optional
 * list of parameters to be inserted into the
 * intent's bundle
 */
inline fun <reified T : Service> startService(vararg bundleExtras: Pair<String, Any?>) {
    val intent = Intent<T>(appCtx)
    intent.putExtras(bundleOf(*bundleExtras))
    appCtx.startService(intent)
}

/**
 * Finish the current application, killing
 * the process and restarting it from scratch
 * on the next restart
 */
fun killApp() {
    Process.killProcess(Process.myPid())
}

/**
 * Object used to serialize and deserialize
 * JSON Strings into Kotlin objects
 */
val NXJson = Json(JsonConfiguration(
    encodeDefaults = false,
    ignoreUnknownKeys = true,
    isLenient = true,
    serializeSpecialFloatingPointValues = true,
    allowStructuredMapKeys = false,
    prettyPrint = true,
    unquotedPrint = false,
    indent = "",
    useArrayPolymorphism = true,
    updateMode = UpdateMode.OVERWRITE
))