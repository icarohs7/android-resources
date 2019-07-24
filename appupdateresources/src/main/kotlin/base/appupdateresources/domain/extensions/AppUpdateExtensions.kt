package base.appupdateresources.domain.extensions

import android.app.Activity
import android.content.Context
import base.appupdateresources.domain.AppUpdateData
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

/**
 * Get the update info of the current app from
 * google play
 */
suspend fun Context.awaitAppUpdateData(): AppUpdateData {
    val manager = AppUpdateManagerFactory.create(this)
    return AppUpdateData(manager, manager.appUpdateInfo.await())
}

/**
 * Whether the current app has updates available
 * for the google play version
 */
val AppUpdateData.isUpdateAvailable: Boolean
    get() = updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE


/**
 * Check if the app has updates available and can be use
 * the in-app update feature and if possible, start the
 * update flow
 */
fun Activity.startImmediateUpdateFlowIfAvailable(
        requestCode: Int,
        updateData: AppUpdateData
) {
    val (manager, info) = updateData
    val hasUpdate = updateData.isUpdateAvailable
    if (hasUpdate && info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
        manager.startUpdateFlowForResult(info, AppUpdateType.IMMEDIATE, this, requestCode)
    }
}

/**
 * Check if the app has updates available and can be use
 * the in-app update feature and if possible, start the
 * flexible update flow
 */
fun Activity.startFlexibleUpdateFlowIfAvailable(
        requestCode: Int,
        updateData: AppUpdateData
) {
    val (manager, info) = updateData
    val hasUpdate = updateData.isUpdateAvailable
    if (hasUpdate && info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
        manager.startUpdateFlowForResult(info, AppUpdateType.FLEXIBLE, this, requestCode)
    }
}