package base.bluetoothresources.domain

import android.bluetooth.BluetoothDevice
import arrow.core.Try
import arrow.core.orNull
import com.github.icarohs7.unoxcore.extensions.getOrElse
import com.github.icarohs7.unoxcore.toplevel.tryBg
import com.sirvar.bluetoothkit.BluetoothKit
import com.sirvar.bluetoothkit.BluetoothKitSocketInterface
import kotlinx.coroutines.withTimeout
import java.io.Closeable
import java.util.UUID

/**
 * Helper wrapping some utilities used to
 * interact with bluetooth devices
 */
class BluetoothHelper : Closeable {
    private val defaultUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    private val bluetoothKit by lazy { BluetoothKit() }
    /**
     * Socket of the currently connected device
     */
    val connectedDeviceSocket: BluetoothKitSocketInterface? = Try { bluetoothKit.bluetoothSocket }.orNull()
    val pairedDevices: List<BluetoothDevice> by lazy { bluetoothKit.pairedDevices.toList() }

    /**
     * Whether there's a device connected or not
     */
    val isConnected get() = bluetoothKit.isConnected()

    /**
     * Connect to the given bluetooth device, changing the state
     * of this singleton, enabling posterior writes to the device,
     * if a device is already connected, automatically disconnect
     * from it first
     * @return Whether the connection was successful or not
     */
    suspend fun connect(
            device: BluetoothDevice,
            uuid: UUID = defaultUUID,
            timeoutMillis: Long = 5000L
    ): Try<Boolean> = tryBg {
        close()
        Try { withTimeout(timeoutMillis) { bluetoothKit.connect(device, uuid) } }.getOrElse(false)
    }

    /**
     * Send the given [data] to the currently connected device
     */
    suspend fun write(data: ByteArray, offset: Int = 0, length: Int = data.size): Try<Unit> = tryBg {
        bluetoothKit.write(data, offset, length)
    }

    /**
     * Send the given [data] to the currently connected device
     */
    suspend fun write(data: String): Try<Unit> = tryBg {
        bluetoothKit.write(data.toByteArray())
    }

    /**
     * Read the next byte of data from the connected device
     * @return the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     */
    suspend fun read(): Try<Int> = tryBg {
        bluetoothKit.read()
    }

    /**
     * Reads up to len bytes of data from the input stream into an array of bytes
     * @param b the buffer into which the data is read.
     * @param off the start offset in the data (defaults to 0)
     * @param len the max number of bytes to write (defaults to the total number of bytes)
     * @return the total number of bytes read into the buffer, or -1 if there is no more data
     */
    suspend fun read(b: ByteArray, off: Int = 0, len: Int = b.size): Try<Int> = tryBg {
        bluetoothKit.read(b, off, len)
    }

    /**
     * Disconnect the device and close all sockets
     */
    override fun close() {
        Try { bluetoothKit.disconnect() }
        Try { connectedDeviceSocket?.close() }
    }
}