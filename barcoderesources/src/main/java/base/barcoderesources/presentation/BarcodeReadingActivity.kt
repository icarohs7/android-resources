package base.barcoderesources.presentation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import arrow.core.Tuple2
import base.barcoderesources.R
import base.barcoderesources.databinding.ActivityBarcodeReadingBinding
import base.barcoderesources.domain.getFlow
import base.coreresources.extensions.requestPermissions
import base.coreresources.extensions.startActivity
import base.coreresources.toplevel.FlashBar
import base.corextresources.presentation._baseclasses.BaseBindingActivity
import com.github.icarohs7.unoxcore.extensions.coroutines.job
import com.google.android.gms.vision.barcode.Barcode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import splitties.init.appCtx
import splitties.resources.appStr
import splitties.resources.str
import timber.log.Timber

class BarcodeReadingActivity : BaseBindingActivity<ActivityBarcodeReadingBinding>() {
    private val barcodeScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        title = str(R.string.ler_codigo_de_barra)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        binding
            .barcodeView
            .drawOverlay()
            .getFlow()
            .onEach { processBarcode(it) }
            .catch { Timber.e(it) }
            .launchIn(barcodeScope)
    }

    override fun onStop() {
        super.onStop()
        barcodeScope.job.cancelChildren()
    }

    private fun processBarcode(barcode: Barcode) {
        _barcodeChannel.offer(Tuple2(this, barcode))
    }

    override fun getLayout(): Int {
        return R.layout.activity_barcode_reading
    }

    companion object {
        @Suppress("ObjectPropertyName")
        private val _barcodeChannel by lazy { Channel<Tuple2<BarcodeReadingActivity, Barcode>>(Channel.CONFLATED) }
        val barcodeChannel: ReceiveChannel<Tuple2<BarcodeReadingActivity, Barcode>> by lazy { _barcodeChannel }

        suspend fun start(fragment: Fragment) {
            if (!requestCameraPermission(fragment)) showNoCameraPermissionError()
            else fragment.requireContext().startActivity<BarcodeReadingActivity>()
        }

        private suspend fun requestCameraPermission(fragment: Fragment): Boolean {
            return fragment.requestPermissions(Manifest.permission.CAMERA)
        }

        private fun showNoCameraPermissionError() {
            FlashBar.error(appStr(R.string.erro_sem_permissao_camera_scan_barcode))
        }
    }
}