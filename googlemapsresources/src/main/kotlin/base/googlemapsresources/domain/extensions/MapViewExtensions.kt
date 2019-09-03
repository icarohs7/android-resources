package base.googlemapsresources.domain.extensions

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun MapView.awaitMap(): GoogleMap {
    return suspendCoroutine { cont ->
        getMapAsync { map ->
            cont.resume(map)
        }
    }
}