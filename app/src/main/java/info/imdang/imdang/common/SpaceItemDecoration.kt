package info.imdang.imdang.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.imdang.imdang.common.ext.dpToPx

class SpaceItemDecoration(private var space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (val layoutManager = parent.layoutManager) {
            is LinearLayoutManager -> {
                when (layoutManager.orientation) {
                    RecyclerView.HORIZONTAL -> {
                        outRect.right += getSpaceByLocation(layoutManager, view, space)
                    }

                    RecyclerView.VERTICAL -> {
                        outRect.bottom += getSpaceByLocation(layoutManager, view, space)
                    }
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
