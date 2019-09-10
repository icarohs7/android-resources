package base.swiperevealresources.presentation.adapters

import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import base.corextresources.domain.extensions.plusAssign
import base.corextresources.presentation.adapters.BaseBindingAdapter
import base.corextresources.presentation.adapters.UnoxAdapterBuilder
import base.corextresources.presentation.adapters.useUnoxAdapter
import base.swiperevealresources.R
import base.swiperevealresources.databinding.LayoutSwipeMenuDeleteBinding
import base.swiperevealresources.databinding.LayoutSwipeMenuEditDeleteBinding
import base.swiperevealresources.databinding.LayoutSwipeMenuRightBinding
import splitties.systemservices.layoutInflater

fun <T> RecyclerView.useRightSwipeContainerAdapter(
        builder: UnoxAdapterBuilder<T, LayoutSwipeMenuRightBinding>.() -> Unit
): BaseBindingAdapter<T, LayoutSwipeMenuRightBinding> {
    return useUnoxAdapter {
        useItemLayout(R.layout.layout_swipe_menu_right)
        builder(this)
    }
}

fun <T> RecyclerView.renderRightSwipeContainerAdapter(
        items: List<T> = emptyList(),
        bindFun: LayoutSwipeMenuRightBinding.(item: T) -> Unit
): BaseBindingAdapter<T, LayoutSwipeMenuRightBinding> {
    return useRightSwipeContainerAdapter {
        bind { item -> bindFun(item) }
        if (items.isNotEmpty()) loadList(items)
    }
}

fun <T> RecyclerView.renderSwipeDeleteMenu(
        items: List<T> = emptyList(),
        bindFun: (item: T, layoutContent: FrameLayout, swipeMenu: LayoutSwipeMenuDeleteBinding) -> Unit
): BaseBindingAdapter<T, LayoutSwipeMenuRightBinding> {
    return useRightSwipeContainerAdapter {
        bind { item ->
            val swipeMenu = LayoutSwipeMenuDeleteBinding.inflate(layoutInflater).apply {
                layoutUnderContent += this
            }
            bindFun(item, layoutOverContent, swipeMenu)
        }
        if (items.isNotEmpty()) loadList(items)
    }
}

/**
 * Example Usage: <br>
 * ```kotlin
 * adapter = recycler.renderSwipeEditDeleteMenu { item, layoutContent, swipeMenu ->
 *     ItemOrderItemBinding.inflate(layoutInflater).apply {
 *         layoutContent += this
 *         root.updateLayoutParams<FrameLayout.LayoutParams> { setMargins(root.dip(6)) }
 *         invalidateItem(item)
 *     }
 *     swipeMenu.apply {
 *         root.updateLayoutParams<FrameLayout.LayoutParams> { setMargins(root.dip(6)) }
 *         setEditHandler { EditOrderItemQuantityDialog(item.a, item.b) }
 *         setRemoveHandler { launch { orderItemRepository.delete(item.a) } }
 *     }
 * }
 * ```
 */
fun <T> RecyclerView.renderSwipeEditDeleteMenu(
        items: List<T> = emptyList(),
        bindFun: (item: T, layoutContent: FrameLayout, swipeMenu: LayoutSwipeMenuEditDeleteBinding) -> Unit
): BaseBindingAdapter<T, LayoutSwipeMenuRightBinding> {
    return useRightSwipeContainerAdapter {
        bind { item ->
            val swipeMenu = LayoutSwipeMenuEditDeleteBinding.inflate(layoutInflater).apply {
                layoutUnderContent += this
            }
            bindFun(item, layoutOverContent, swipeMenu)
        }
        if (items.isNotEmpty()) loadList(items)
    }
}