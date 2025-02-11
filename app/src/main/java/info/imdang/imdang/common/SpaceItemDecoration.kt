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
        val position = parent.getChildAdapterPosition(view)
        val isLast = position == parent.adapter?.itemCount?.minus(1)
        when (val layoutManager = parent.layoutManager) {
            is LinearLayoutManager -> {
                when (layoutManager.orientation) {
                    RecyclerView.HORIZONTAL -> {
                        outRect.right += getSpaceByLocation(isLast, view.context.dpToPx(space))
                    }

                    RecyclerView.VERTICAL -> {
                        outRect.bottom += getSpaceByLocation(isLast, view.context.dpToPx(space))
                    }
                }
            }
        }
    }

    private fun getSpaceByLocation(
        isLast: Boolean,
        space: Int
    ): Int = if (isLast) 0 else space
}
