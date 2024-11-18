package info.imdang.imdang.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

private fun getView(
    parent: ViewGroup,
    @LayoutRes layoutResourceId: Int
): View = LayoutInflater
    .from(parent.context)
    .inflate(layoutResourceId, parent, false)

class BaseViewHolder(
    parent: ViewGroup,
    @LayoutRes private val layoutResourceId: Int,
    private val bindingItemId: Int,
    private val viewModel: Map<Int, Any>
) : RecyclerView.ViewHolder(getView(parent, layoutResourceId)) {
    private val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

    fun bind(item: Any) {
        binding.setVariable(bindingItemId, item)

        for (key in viewModel.keys) {
            binding.setVariable(key, viewModel[key])
        }
        binding.executePendingBindings()
    }

    fun bind(item: Any, click: (item: Any, pos: Int) -> Unit, pos: Int) {
        bind(item)
        binding.root.setOnClickListener { click(item, pos) }
    }
}
