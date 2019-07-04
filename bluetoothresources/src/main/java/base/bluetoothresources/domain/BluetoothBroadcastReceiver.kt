package base.bluetoothresources.domain

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class BluetoothBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action ?: return) {
            BluetoothAdapter.ACTION_STATE_CHANGED -> onStateChange(intent)
            BluetoothDevice.ACTION_ACL_CONNECTED -> onLowEnergyDeviceConnected(intent)
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> onLowEnergyDeviceDisconnected(intent)
        }
    }

    private fun onStateChange(intent: Intent) {
        when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
            BluetoothAdapter.STATE_OFF -> relay.accept(Event.BluetoothOff)
            BluetoothAdapter.STATE_TURNING_OFF -> relay.accept(Event.BluetoothTurningOff)
            BluetoothAdapter.STATE_ON -> relay.accept(Event.BluetoothOn)
            BluetoothAdapter.STATE_TURNING_ON -> relay.accept(Event.BluetoothTurningOn)
        }
    }

    private fun onLowEnergyDeviceConnected(intent: Intent) {
        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        relay.accept(Event.DeviceConnected(device))
    }

    private fun onLowEnergyDeviceDisconnected(intent: Intent) {
        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        relay.accept(Event.DeviceDisconnected(device))
    }

    companion object {
        private val relay by lazy { PublishRelay.create<Event>() }
        val events: Flowable<Event> get() = relay.hide().toFlowable(BackpressureStrategy.LATEST)
    }

    sealed class Event {
        object BluetoothOff : Event()
        object BluetoothTurningOff : Event()
        object BluetoothOn : Event()
        object BluetoothTurningOn : Event()
        class DeviceConnected(val device: BluetoothDevice) : Event()
        class DeviceDisconnected(val device: BluetoothDevice) : Event()
    }
}