package base.fluxarchresources

import base.coreresources.extensions.unit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

/**
 * State manager using [S] as the state type
 * and a Flow<S> observable type
 */
class FlowStateManager<S>(initialState: S? = null) {
    private val scope = GlobalScope
    private val channel = initialState?.let { ConflatedBroadcastChannel(it) } ?: ConflatedBroadcastChannel()
    private val dispatcherActor = scope.actor<SuspendReducer<S>> { consumeEach { handleDispatch(it) } }

    val state: S get() = channel.value
    fun setState(reducer: SuspendReducer<S>): Unit = dispatcherActor.offer(reducer).unit
    fun flow(): Flow<S> = channel.asFlow()
    private suspend fun handleDispatch(reducer: SuspendReducer<S>) {
        channel.send(reducer(channel.value)).unit
    }
}