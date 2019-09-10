package base.corextresources.domain.extensions

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import base.corextresources.domain.callbacks.SimpleSwipeCallback

fun RecyclerView.onItemSwipe(onSwiped: (viewHolder: RecyclerView.ViewHolder, direction: Int) -> Unit) {
    val callback = object : SimpleSwipeCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwiped(viewHolder, direction)
        }
    }
    ItemTouchHelper(callback).attachToRecyclerView(this)
}