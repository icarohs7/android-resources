package base.locationresources.domain.toplevel

import android.location.Location
import android.location.LocationManager
import arrow.core.Try
import com.github.icarohs7.unoxcore.extensions.coroutines.onForeground
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import splitties.init.appCtx
import splitties.systemservices.locationManager

/**
 * Get the current location of the user
 */
suspend fun getCurrentLocation(): Try<Location> {
    return Try { getLocationFlow { }.first() }
}

/**
 * Flow from a stream of location emissions from
 * the fused location API.
 * [Source][https://gist.github.com/LouisCAD/0a648e2b49942acd2acbb693adfaa03a]
 */
inline fun getLocationFlow(configLocationRequest: LocationRequest.() -> Unit): Flow<Location> {
    return getLocationFlow(LocationRequest.create().apply(configLocationRequest))
}

/**
 * Flow from a stream of location emissions from
 * the fused location API.
 * [Source][https://gist.github.com/LouisCAD/0a648e2b49942acd2acbb693adfaa03a]
 */
fun getLocationFlow(locationRequest: LocationRequest): Flow<Location> {
    return callbackFlow {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            error("Location provider is disabled")
        }

        val locationClient = LocationServices.getFusedLocationProviderClient(appCtx)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.locations.forEach { runCatching { offer(it) } }
            }
        }
        locationClient.lastLocation.await()?.let { send(it) }
        onForeground { locationClient.requestLocationUpdates(locationRequest, locationCallback, null).await() }
        awaitClose { locationClient.removeLocationUpdates(locationCallback) }
    }
}