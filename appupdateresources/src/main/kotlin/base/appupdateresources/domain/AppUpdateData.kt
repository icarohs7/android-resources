package base.appupdateresources.domain

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager

class AppUpdateData(
        val manager: AppUpdateManager,
        val updateInfo: AppUpdateInfo
) {
    operator fun component1(): AppUpdateManager = manager
    operator fun component2(): AppUpdateInfo = updateInfo
}