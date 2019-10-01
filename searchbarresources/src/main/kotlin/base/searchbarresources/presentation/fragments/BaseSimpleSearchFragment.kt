package base.searchbarresources.presentation.fragments

import base.corextresources.presentation._baseclasses.BaseBindingFragment
import base.searchbarresources.R
import base.searchbarresources.databinding.FragmentBaseSimpleSearchBinding

abstract class BaseSimpleSearchFragment : BaseBindingFragment<FragmentBaseSimpleSearchBinding>() {
    override fun getLayout(): Int = R.layout.fragment_base_simple_search
}