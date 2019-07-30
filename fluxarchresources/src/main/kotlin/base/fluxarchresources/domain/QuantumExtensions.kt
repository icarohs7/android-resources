package base.fluxarchresources.domain

import io.sellmair.quantum.Quantum
import io.sellmair.quantum.StateListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T> Quantum<T>.flow(): Flow<T> = callbackFlow {
    val listener: StateListener<T> = { offer(it) }
    addStateListener(listener)
    awaitClose { removeStateListener(listener) }
}