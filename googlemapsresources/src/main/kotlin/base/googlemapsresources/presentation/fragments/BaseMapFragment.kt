package base.googlemapsresources.presentation.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import base.corextresources.domain.extensions.viewScope
import com.airbnb.mvrx.BaseMvRxFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import splitties.views.existingOrNewId
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class BaseMapFragment : BaseMvRxFragment() {
    private val mapFragment get() = childFragmentManager.findFragmentByTag("mapFragment")

    fun withMap(callback: (GoogleMap) -> Unit) {
        viewScope.launchWhenStarted {
            val frag = childFragmentManager.findFragmentByTag("mapFragment") as SupportMapFragment
            frag.getMapAsync(callback)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mapFragment == null) childFragmentManager.commit {
            add(getMapContainer().existingOrNewId, SupportMapFragment(), "mapFragment")
        }
    }

    override fun invalidate() {
    }

    suspend fun awaitMap() = suspendCoroutine<GoogleMap> { cont -> withMap { cont.resume(it) } }
    abstract fun getMapContainer(): ViewGroup
}