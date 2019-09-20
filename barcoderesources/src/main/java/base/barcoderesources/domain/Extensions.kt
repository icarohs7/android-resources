package base.barcoderesources.domain

import androidx.fragment.app.Fragment
import arrow.core.Tuple2
import base.barcoderesources.presentation.BarcodeReadingActivity
import base.corextresources.domain.extensions.asFlow
import base.corextresources.domain.extensions.viewScope
import com.bobekos.bobek.scanner.BarcodeView
import com.github.icarohs7.unoxcore.extensions.coroutines.forEach
import com.google.android.gms.vision.barcode.Barcode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @param onBarcodeReaded: Callback invoked for each barcode read. Return true to stop reading
 */
fun Fragment.readBarcode(onBarcodeReaded: suspend (Tuple2<BarcodeReadingActivity, Barcode>) -> Boolean): Job {
    return viewScope.launch {
        BarcodeReadingActivity.start(this@readBarcode)
        BarcodeReadingActivity.barcodeChannel.forEach {
            if (onBarcodeReaded(it)) {
                it.a.finish()
                return@forEach
            }
        }
    }
}

fun BarcodeView.getFlow(): Flow<Barcode> {
    return getObservable().asFlow()
}