package base.corextresources.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import base.corextresources.R
import base.corextresources.databinding.FragmentSwipeRecyclerBinding
import base.corextresources.presentation._baseclasses.BaseBindingFragment

abstract class BaseSwipeRecyclerFragment : BaseBindingFragment<FragmentSwipeRecyclerBinding>() {
    override fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        super.onBindingCreated(inflater, container, savedInstanceState)
        binding.stateView.hideStates()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    fun displayState(stateTag: String) {
        binding.stateView.displayState(stateTag)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_swipe_recycler
    }
}