package base.searchbarresources.presentation.fragments

import base.corextresources.presentation._baseclasses.BaseBindingFragment
import base.searchbarresources.R
import base.searchbarresources.databinding.FragmentBaseSimpleSearchFabBinding

abstract class BaseSimpleSearchFabFragment : BaseBindingFragment<FragmentBaseSimpleSearchFabBinding>() {
    override fun getLayout(): Int = R.layout.fragment_base_simple_search_fab
}