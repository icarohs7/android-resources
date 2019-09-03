package base.googlemapsresources.presentation.fragments

import android.os.Bundle
import com.airbnb.mvrx.BaseMvRxFragment
import com.google.android.gms.maps.MapView

abstract class BaseMapFragment : BaseMvRxFragment() {
    val mapView: MapView by lazy { getMap() }

    abstract fun getMap(): MapView

    override fun invalidate() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}