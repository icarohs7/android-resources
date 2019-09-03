package base.googlemapsresources.domain.extensions

import base.googlemapsresources.presentation.fragments.BaseMapFragment
import com.google.android.gms.maps.GoogleMap

suspend fun BaseMapFragment.awaitMap(): GoogleMap {
    return mapView.awaitMap()
}