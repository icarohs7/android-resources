package base.barcoderesources.presentation

import android.Manifest
import android.os.Bundle
import arrow.core.Tuple2
import arrow.core.toT
import base.barcoderesources.R
import base.barcoderesources.databinding.ActivityBarcodeReadingBinding
import com.github.icarohs7.unoxandroidarch.extensions.requestPermissions
import com.github.icarohs7.unoxandroidarch.extensions.startActivity
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseBindingActivity
import com.github.icarohs7.unoxandroidarch.presentation.fragments.BaseScopedFragment
import com.github.icarohs7.unoxandroidarch.toplevel.showErrorFlashBar
import com.google.android.gms.vision.barcode.Barcode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onStop
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import splitties.init.appCtx
import splitties.resources.appStr
import splitties.resources.str
import timber.log.Timber

class BarcodeReadingActivity : BaseBindingActivity<ActivityBarcodeReadingBinding>() {

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        title = str(R.string.ler_codigo_de_barra)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        binding.barcodeView
                .drawOverlay()
                .getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::processBarcode, Timber::e)
                .disposeBy(onStop)
    }

    private fun processBarcode(barcode: Barcode) {
        _barcodeChannel.offer(this toT barcode)
    }

    override fun getLayout(): Int {
        return R.layout.activity_barcode_reading
    }

    companion object {
        @Suppress("ObjectPropertyName")
        private val _barcodeChannel by lazy { Channel<Tuple2<BarcodeReadingActivity, Barcode>>(Channel.CONFLATED) }
        val barcodeChannel: ReceiveChannel<Tuple2<BarcodeReadingActivity, Barcode>> by lazy { _barcodeChannel }

        suspend fun start(fragment: BaseScopedFragment) {
            if (!requestCameraPermission(fragment)) showNoCameraPermissionError()
            else appCtx.startActivity<BarcodeReadingActivity>()
        }

        private suspend fun requestCameraPermission(fragment: BaseScopedFragment): Boolean {
            return fragment.requestPermissions(Manifest.permission.CAMERA)
        }

        private fun showNoCameraPermissionError() {
            showErrorFlashBar(appStr(R.string.erro_sem_permissao_camera_scan_barcode))
        }
    }
}