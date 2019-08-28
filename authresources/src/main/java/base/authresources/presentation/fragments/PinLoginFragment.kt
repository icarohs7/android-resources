package base.authresources.presentation.fragments

import androidx.core.os.bundleOf
import base.authresources.R
import base.authresources.databinding.FragmentPinLoginBinding
import base.authresources.domain.AuthenticationType

class PinLoginFragment : BaseLoginFragment<FragmentPinLoginBinding>() {
    private val length by lazy { arguments?.getInt(LENGTH_ARG) ?: 5 }

    override fun onBindingCreated() {
        super.onBindingCreated()
        setupBinding()
    }

    private fun setupBinding(): Unit = with(binding) {
        editPin.digitLength = length
        editPin.setOnTextChangeListener { text: String? ->
            if (text?.length == length) {
                handleLogin(AuthenticationType.Pin(text))
                editPin.reset()
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_pin_login
    }

    companion object {
        const val LENGTH_ARG: String = "length"

        fun create(pinLength: Int): PinLoginFragment {
            val frag = PinLoginFragment()
            frag.arguments = bundleOf(LENGTH_ARG to pinLength)
            return frag
        }
    }
}