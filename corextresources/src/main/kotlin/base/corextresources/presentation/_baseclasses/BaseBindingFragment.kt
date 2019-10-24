package base.corextresources.presentation._baseclasses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.BaseMvRxFragment
import androidx.lifecycle.asFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Base fragment using databinding
 */
abstract class BaseBindingFragment<B : ViewDataBinding> : BaseMvRxFragment() {
    /**
     * Initialized on [onCreateView]
     */
    lateinit var binding: B
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
            .inflate<B>(inflater, getLayout(), container, false)
            .apply { lifecycleOwner = this@BaseBindingFragment }

        onBindingCreated(inflater, container, savedInstanceState)
        afterCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    /**
     * Called after the binding is created,
     * inside the onCreateView lifecycle
     * event
     */
    open fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
    }

    /**
     * Called just after the [binding] is defined,
     * inside the onCreateView event
     */
    open fun afterCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
    }

    override fun invalidate() {
    }

    /**
     * Convert the given live data to flow,
     * add an onEach operator to it with the
     * given action and return the flow
     */
    fun <T> LiveData<T>.asFlowOnEach(action: suspend (T) -> Unit): Flow<T> {
        return asFlow().onEach(action)
    }

    /**
     * @return layout to setup data binding.
     */
    @LayoutRes
    abstract fun getLayout(): Int
}