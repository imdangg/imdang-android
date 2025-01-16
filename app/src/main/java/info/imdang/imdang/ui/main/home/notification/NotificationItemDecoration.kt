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
        val nextItemViewType = if (position + 1 < layoutManager.itemCount) {
            parent.adapter?.getItemViewType(position + 1)
        } else {
            null
        }
        val titleViewType = NotificationItemHolderType.TitleHolder.ordinal
        val notificationViewType = NotificationItemHolderType.NotificationHolder.ordinal

        if (layoutManager is LinearLayoutManager) {
            when {
                viewType == titleViewType && nextItemViewType == notificationViewType -> {
                    outRect.bottom += getSpaceByLocation(layoutManager, view, 16)
                }
                viewType == notificationViewType && nextItemViewType == notificationViewType -> {
                    outRect.bottom += getSpaceByLocation(layoutManager, view, 12)
                }
                viewType == notificationViewType && nextItemViewType == titleViewType -> {
                    outRect.bottom += getSpaceByLocation(layoutManager, view, 24)
                }
            }
        }
    }

    private fun getSpaceByLocation(
        layoutManager: LinearLayoutManager,
        view: View,
        space: Int
    ): Int {
        val isLast = layoutManager.getPosition(view) == layoutManager.itemCount - 1
        return if (isLast) 0 else view.context.dpToPx(space)
    }
}
