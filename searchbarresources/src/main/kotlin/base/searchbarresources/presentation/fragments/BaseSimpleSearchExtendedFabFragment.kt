package base.searchbarresources.presentation.fragments

import base.corextresources.presentation._baseclasses.BaseBindingFragment
import base.searchbarresources.R
import base.searchbarresources.databinding.FragmentBaseSimpleSearchExtendedFabBinding

abstract class BaseSimpleSearchExtendedFabFragment : BaseBindingFragment<FragmentBaseSimpleSearchExtendedFabBinding>() {
    override fun getLayout(): Int = R.layout.fragment_base_simple_search_extended_fab
}