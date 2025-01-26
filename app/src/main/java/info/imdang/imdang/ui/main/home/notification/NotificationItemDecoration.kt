package info.imdang.imdang.ui.main.home.notification

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.imdang.imdang.common.ext.dpToPx
import info.imdang.imdang.ui.main.home.notification.NotificationActivity.NotificationItemHolderType

class NotificationItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager ?: return
        val position = parent.getChildAdapterPosition(view).takeIf {
            it > -1
        } ?: return
        val viewType = parent.adapter?.getItemViewType(position) ?: return
        val prevItemViewType = if (position - 1 >= 0) {
            parent.adapter?.getItemViewType(position - 1)
        } else {
            null
        }
        val titleViewType = NotificationItemHolderType.TitleHolder.ordinal
        val notificationViewType = NotificationItemHolderType.NotificationHolder.ordinal
        val emptyViewType = NotificationItemHolderType.EmptyHolder.ordinal

        if (layoutManager is LinearLayoutManager) {
            val space = when (viewType) {
                titleViewType -> 24
                notificationViewType -> if (prevItemViewType == titleViewType) 16 else 12
                emptyViewType -> if (prevItemViewType == titleViewType) 16 else 24
                else -> 12
            }
            outRect.top += getSpaceByLocation(view, space)
        }
    }

    private fun getSpaceByLocation(view: View, space: Int): Int = view.context.dpToPx(space)
}
