package base.bluetoothprinterresources.domain.extensions

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.RequiresPermission
import base.bluetoothprinterresources.R
import base.bluetoothresources.domain.BluetoothHelper
import base.corextresources.domain.toplevel.kget
import base.corextresources.domain.toplevel.toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.github.icarohs7.unoxcore.extensions.getOrElse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Print the given content on the connected bluetooth device
 * or the device selected by the user in the selection dialog
 * shown
 */
@RequiresPermission(value = Manifest.permission.BLUETOOTH)
suspend fun Context.printContent(content: ByteArray) {
    coroutineScope {
        val bluetoothHelper = kget<BluetoothHelper>()
        if (bluetoothHelper.isConnected) {
            bluetoothHelper.write(content)
        } else {
            suspendCoroutine<Unit> { cont ->
                operateOnSelectedDevice(R.string.selecione_a_impressora) { selected ->
                    cont.resume(Unit)
                    launch {
                        connectToBluetoothDevice(bluetoothHelper, selected)
                        val success = bluetoothHelper.connect(selected).getOrElse(false)
                        if (success) bluetoothHelper.write(content)
                        else toast(R.string.houve_um_erro_ao_conectar_se_aa_impressora)
                    }
                }
            }
        }
    }
}

/**
 * Print the given image on the connected bluetooth device
 * or the device selected by the user in the selection dialog
 * shown
 */
@RequiresPermission(value = Manifest.permission.BLUETOOTH)
suspend fun Context.printImageViaBluetooth(content: Bitmap) {
    coroutineScope {
        val bluetoothHelper = kget<BluetoothHelper>()
        if (bluetoothHelper.isConnected) {
            bluetoothHelper.printImage(content)
        } else {
            operateOnSelectedDevice(R.string.selecione_a_impressora) { selected ->
                launch {
                    connectToBluetoothDevice(bluetoothHelper, selected)
                    val success = bluetoothHelper.connect(selected).getOrElse(false)
                    if (success) bluetoothHelper.printImage(content)
                    else toast(R.string.houve_um_erro_ao_conectar_se_aa_impressora)
                }
            }
        }
    }
}

/***
 * Shows a dialog to select a bluetooth device and runs the given
 * callback passing the selected devices
 */
@RequiresPermission(value = Manifest.permission.BLUETOOTH)
fun Context.operateOnSelectedDevice(
    dialogTitleRes: Int,
    onDismiss: () -> Unit = {},
    fn: (selected: BluetoothDevice) -> Unit
) {
    val devices = kget<BluetoothHelper>().pairedDevices
    MaterialDialog(this@operateOnSelectedDevice).show {
        title(dialogTitleRes)
        onDismiss { onDismiss() }
        listItemsSingleChoice(items = devices.map { "${it.name} - ${it.address}" }) { _, index, _ ->
            val selected = devices[index]
            fn(selected)
        }
    }
}

/**
 * Open a connection to the given bluetooth device
 */
@RequiresPermission(value = Manifest.permission.BLUETOOTH)
suspend fun connectToBluetoothDevice(bluetoothHelper: BluetoothHelper, deviceAddress: String): Boolean {
    val result = bluetoothHelper.connect(deviceAddress)
    val success = result.getOrElse(false)
    if (!success) toast(R.string.houve_um_erro_ao_conectar_se_aa_impressora)

    return success
}

/**
 * Open a connection to the given bluetooth device
 */
@RequiresPermission(value = Manifest.permission.BLUETOOTH)
suspend fun connectToBluetoothDevice(bluetoothHelper: BluetoothHelper, device: BluetoothDevice): Boolean {
    val result = bluetoothHelper.connect(device)
    val success = result.getOrElse(false)
    if (!success) toast(R.string.houve_um_erro_ao_conectar_se_aa_impressora)

    return success
}