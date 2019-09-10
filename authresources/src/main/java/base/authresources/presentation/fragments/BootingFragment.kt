package base.authresources.presentation.fragments

import base.authresources.R
import base.authresources.databinding.FragmentBootingBinding
import base.corextresources.presentation._baseclasses.BaseBindingFragment

class BootingFragment : BaseBindingFragment<FragmentBootingBinding>() {
    override fun getLayout(): Int {
        return R.layout.fragment_booting
    }
}