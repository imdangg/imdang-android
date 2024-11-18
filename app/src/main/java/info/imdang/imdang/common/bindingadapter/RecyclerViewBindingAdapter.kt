package info.imdang.imdang.common.bindingadapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.imdang.imdang.base.BaseViewHolder

@BindingAdapter("bindItemList")
fun RecyclerView.bindItemList(item: List<Any>?) {
    if (item == null) return

    @Suppress("UNCHECKED_CAST")
    (adapter as? BaseSingleViewAdapter<Any>)?.run {
        submitList(item)
    }
}

class BaseSingleViewAdapter<ITEM : Any>(
    @LayoutRes private val layoutResourceId: Int,
    private val bindingItemId: Int,
    private val viewModel: Map<Int, Any>,
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : ListAdapter<ITEM, BaseViewHolder>(diffUtil) {

    private var itemClickListener: ((item: Any, pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(
            parent = parent,
            layoutResourceId = layoutResourceId,
            bindingItemId = bindingItemId,
            viewModel = viewModel
        )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (itemClickListener == null) {
            holder.bind(getItem(position))
        } else {
            holder.bind(getItem(position), itemClickListener!!, position)
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItem(position: Int): ITEM {
        return super.getItem(
            if (currentList.isNotEmpty()) {
                position % currentList.size
            } else {
                position
            }
        )
    }
}
