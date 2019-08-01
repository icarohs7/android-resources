package base.barcoderesources.domain

import arrow.core.Tuple2
import base.barcoderesources.presentation.BarcodeReadingActivity
import base.corelibrary.domain.extensions.asFlow
import com.bobekos.bobek.scanner.BarcodeView
import com.github.icarohs7.unoxandroidarch.presentation.fragments.BaseScopedFragment
import com.github.icarohs7.unoxcore.extensions.coroutines.forEach
import com.google.android.gms.vision.barcode.Barcode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun BaseScopedFragment.readBarcode(onBarcodeReaded: suspend (Tuple2<BarcodeReadingActivity, Barcode>) -> Boolean): Job {
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