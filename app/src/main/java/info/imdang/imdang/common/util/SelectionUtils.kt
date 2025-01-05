package info.imdang.imdang.common.util

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat

object SelectionUtils {

    fun setupSelectionHandlers(
        items: Map<String, TextView>,
        toggleSelection: (String) -> Unit
    ) {
        items.forEach { (key, textView) ->
            textView.setOnClickListener { toggleSelection(key) }
        }
    }

    fun updateMultiSelectionUI(
        items: Map<String, TextView>,
        selectedItems: Set<String>,
        context: Context
    ) {
        val selectedBackgroundRes = info.imdang.component.R.drawable.sr_orange50_orange500_all8
        val selectedTextColorRes = info.imdang.component.R.color.orange_500
        val defaultBackgroundRes = info.imdang.component.R.drawable.sr_white_gray100_all8
        val defaultTextColorRes = info.imdang.component.R.color.gray_400

        items.forEach { (key, textView) ->
            if (selectedItems.contains(key)) {
                textView.setBackgroundResource(selectedBackgroundRes)
                textView.setTextColor(ContextCompat.getColor(context, selectedTextColorRes))
            } else {
                textView.setBackgroundResource(defaultBackgroundRes)
                textView.setTextColor(ContextCompat.getColor(context, defaultTextColorRes))
            }
        }
    }

    fun updateSingleSelectionUI(
        items: Map<String, TextView>,
        selectedItem: String?,
        context: Context
    ) {
        val selectedBackgroundRes = info.imdang.component.R.drawable.sr_orange50_orange500_all8
        val selectedTextColorRes = info.imdang.component.R.color.orange_500
        val defaultBackgroundRes = info.imdang.component.R.drawable.sr_white_gray100_all8
        val defaultTextColorRes = info.imdang.component.R.color.gray_400

        items.forEach { (key, textView) ->
            if (key == selectedItem) {
                textView.setBackgroundResource(selectedBackgroundRes)
                textView.setTextColor(ContextCompat.getColor(context, selectedTextColorRes))
            } else {
                textView.setBackgroundResource(defaultBackgroundRes)
                textView.setTextColor(ContextCompat.getColor(context, defaultTextColorRes))
            }
        }
    }
}
