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

        if (position == RecyclerView.NO_POSITION) return

        when (val layoutManager = parent.layoutManager) {
            is LinearLayoutManager -> {
                when (layoutManager.orientation) {
                    RecyclerView.HORIZONTAL -> {
                        outRect.right += view.context.dpToPx(space)
                    }

                    RecyclerView.VERTICAL -> {
                        outRect.bottom += view.context.dpToPx(space)
                    }
                }
            }
        }
    }
}
