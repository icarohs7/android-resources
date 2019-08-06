package base.barcoderesources.domain

import androidx.fragment.app.Fragment
import arrow.core.Tuple2
import base.barcoderesources.presentation.BarcodeReadingActivity
import base.corelibrary.domain.extensions.asFlow
import base.corelibrary.domain.extensions.coroutines.launch
import com.bobekos.bobek.scanner.BarcodeView
import com.github.icarohs7.unoxcore.extensions.coroutines.forEach
import com.google.android.gms.vision.barcode.Barcode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

fun Fragment.readBarcode(onBarcodeReaded: suspend (Tuple2<BarcodeReadingActivity, Barcode>) -> Boolean): Job {
    return launch {
        BarcodeReadingActivity.start(this@readBarcode)
        BarcodeReadingActivity.barcodeChannel.forEach {
            if (onBarcodeReaded(it)) return@forEach
        }
    }
}

fun BarcodeView.getFlow(): Flow<Barcode> {
    return getObservable().asFlow()
}