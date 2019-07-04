package base.barcoderesources.domain

import arrow.core.Tuple2
import base.barcoderesources.presentation.BarcodeReadingActivity
import com.github.icarohs7.unoxandroidarch.presentation.fragments.BaseScopedFragment
import com.github.icarohs7.unoxcore.extensions.coroutines.forEach
import com.google.android.gms.vision.barcode.Barcode
import kotlinx.coroutines.launch

fun BaseScopedFragment.readBarcode(onBarcodeReaded: suspend (Tuple2<BarcodeReadingActivity, Barcode>) -> Boolean) {
    launch {
        BarcodeReadingActivity.start(this@readBarcode)
        BarcodeReadingActivity.barcodeChannel.forEach {
            if (onBarcodeReaded(it)) return@forEach
        }
    }
}