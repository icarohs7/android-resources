package base.corextresources.domain.extensions

import com.github.icarohs7.unoxcore.UnoxCore
import com.github.icarohs7.unoxcore.extensions.coroutines.flowOnBackground
import com.umutbey.stateviews.StateView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * @param stateTag Tag of the state to be shown when the given
 * flow emits an empty list
 */
fun StateView.displayStateWhenFlowListIsEmpty(scope: CoroutineScope, flow: Flow<List<*>>, stateTag: String): Job {
    return flow.map { it.isNotEmpty() }
            .distinctUntilChanged()
            .flowOnBackground()
            .onEach { showContent -> if (showContent) hideStates() else displayState(stateTag) }
            .flowOn(UnoxCore.foregroundDispatcher)
            .launchIn(scope)
}