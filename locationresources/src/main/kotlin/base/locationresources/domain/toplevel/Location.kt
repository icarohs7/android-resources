package base.locationresources.domain.toplevel

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import arrow.core.Try
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.tasks.await
import splitties.init.appCtx
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Try to get the current location using the given means by default:
 * - Try to request the current location using GPS
 * - Try to request the current location using the Network
 * - Try to get the last known location acquired through GPS
 * - Try to get the last known location acquired through the Network
 */
@RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
suspend fun getCurrentLocation(): Try<Location> {
    return Try {
        suspendCoroutine<Location?> { cont ->
            val smartLocation = SmartLocation.with(appCtx).location()
            val locationState = smartLocation.state()
            when {
                !locationState.locationServicesEnabled() -> cont.resume(null)
                !locationState.isAnyProviderAvailable -> cont.resume(null)
                else -> smartLocation.oneFix().start { cont.resume(it) }
            }
        }!!
    }
}

/**
 * Flow from a stream of location emissions from
 * the fused location API.
 * [Source][https://gist.github.com/LouisCAD/0a648e2b49942acd2acbb693adfaa03a]
 */
inline fun fusedLocationFlow(configLocationRequest: LocationRequest.() -> Unit): Flow<Location> {
    return fusedLocationFlow(LocationRequest.create().apply(configLocationRequest))
}

/**
 * Flow from a stream of location emissions from
 * the fused location API.
 * [Source][https://gist.github.com/LouisCAD/0a648e2b49942acd2acbb693adfaa03a]
 */
fun fusedLocationFlow(
        locationRequest: LocationRequest
): Flow<Location> = channelFlow {
    val locationClient = LocationServices.getFusedLocationProviderClient(appCtx)
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.locations.forEach { runCatching { offer(it) } }
        }
    }
    send(locationClient.lastLocation.await())
    locationClient.requestLocationUpdates(locationRequest, locationCallback, null).await()
    awaitClose { locationClient.removeLocationUpdates(locationCallback) }
}