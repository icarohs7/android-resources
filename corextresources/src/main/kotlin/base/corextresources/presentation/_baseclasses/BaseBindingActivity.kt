package base.corextresources.presentation._baseclasses

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import base.coreresources.presentation.activities.BaseArchActivity

abstract class BaseBindingActivity<B : ViewDataBinding> : BaseArchActivity() {
    /**
     * Initialized in [onCreate]
     */
    lateinit var binding: B
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())
        binding.lifecycleOwner = this
        onBindingCreated(savedInstanceState)
        afterInitialSetup(savedInstanceState)
    }

    /**
     * Called after the databinding of the fragment is set
     */
    open fun onBindingCreated(savedInstanceState: Bundle?) {
    }

    /**
     * Called after [onBindingCreated]
     */
    open fun afterInitialSetup(savedInstanceState: Bundle?) {
    }

    /**
     * @return layout to setup data binding.
     */
    @LayoutRes
    abstract fun getLayout(): Int
}