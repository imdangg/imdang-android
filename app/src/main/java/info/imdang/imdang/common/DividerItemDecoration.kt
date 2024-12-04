package info.imdang.imdang.common

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import info.imdang.imdang.common.ext.dpToPx

class DividerItemDecoration(
    @ColorInt
    private val color: Int = Color.parseColor("#E6E6E6"),
    private val padding: Float = 0f,
    private val height: Int = 1
) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val start = parent.paddingStart + padding
        val end = parent.width - parent.paddingEnd - padding

        repeat(parent.childCount) {
            val child = parent.getChildAt(it)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + parent.context.dpToPx(if (it < parent.childCount - 1) height else 0)

            c.drawRect(start, top, end, bottom, paint)
        }
    }
}
