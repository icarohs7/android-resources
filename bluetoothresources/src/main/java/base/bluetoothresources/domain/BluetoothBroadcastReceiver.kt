package base.bluetoothresources.domain

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import base.corelibrary.domain.extensions.coroutines.PublishSubjectFlow
import kotlinx.coroutines.flow.Flow

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
            BluetoothAdapter.STATE_OFF -> dataFlow.offer(Event.BluetoothOff)
            BluetoothAdapter.STATE_TURNING_OFF -> dataFlow.offer(Event.BluetoothTurningOff)
            BluetoothAdapter.STATE_ON -> dataFlow.offer(Event.BluetoothOn)
            BluetoothAdapter.STATE_TURNING_ON -> dataFlow.offer(Event.BluetoothTurningOn)
        }
    }

    private fun onLowEnergyDeviceConnected(intent: Intent) {
        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE) ?: return
        dataFlow.offer(Event.DeviceConnected(device))
    }

    private fun onLowEnergyDeviceDisconnected(intent: Intent) {
        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE) ?: return
        dataFlow.offer(Event.DeviceDisconnected(device))
    }

    companion object {
        private val dataFlow by lazy { PublishSubjectFlow<Event>() }
        val eventFlow: Flow<Event> get() = dataFlow.flow()
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