package base.authresources.presentation.fragments

import base.authresources.R
import base.authresources.databinding.FragmentBootingBinding
import com.github.icarohs7.unoxandroidarch.presentation.fragments.BaseBindingFragment

class BootingFragment : BaseBindingFragment<FragmentBootingBinding>() {
    override fun getLayout(): Int {
        return R.layout.fragment_booting
    }
}