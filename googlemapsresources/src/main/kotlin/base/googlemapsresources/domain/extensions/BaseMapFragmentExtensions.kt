package base.googlemapsresources.domain.extensions

import base.googlemapsresources.presentation.fragments.BaseMapFragment
import com.google.android.gms.maps.GoogleMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

suspend fun BaseMapFragment.awaitMap(): GoogleMap {
    return mapView.awaitMap()
}

fun BaseMapFragment.withMap(callback: GoogleMap.() -> Unit) {
    mapView.getMapAsync(callback)
}

fun BaseMapFragment.withMapAsync(scope: CoroutineScope, callback: suspend GoogleMap.() -> Unit): Job {
    return scope.launch { with(awaitMap()) { callback() } }
}