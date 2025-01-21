package info.imdang.imdang.common.bindingadapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.imdang.imdang.base.BaseViewHolder
import info.imdang.imdang.model.insight.InsightDetailItem
import info.imdang.imdang.ui.insight.InsightDetailAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@BindingAdapter("bindItemList")
fun RecyclerView.bindItemList(item: List<Any>?) {
    if (item == null) return

    @Suppress("UNCHECKED_CAST")
    (adapter as? BaseSingleViewAdapter<Any>)?.run {
        submitList(item)
    }

    @Suppress("UNCHECKED_CAST")
    (adapter as? BaseMultiViewAdapter<Any>)?.run {
        val newItems = mutableListOf<Any>()
        item.forEach {
            newItems.add(it)
        }
        submitList(newItems)
    }

    (adapter as? InsightDetailAdapter)?.run {
        val newItems = mutableListOf<InsightDetailItem>()
        item.forEach {
            newItems.add(it as InsightDetailItem)
        }
        submitList(newItems)
    }
}

@BindingAdapter(
    value = ["bindPagingItemList", "bindLifecycleOwner"],
    requireAll = true
)
fun RecyclerView.bindPagingItemList(item: PagingData<Any>?, lifecycleOwner: LifecycleOwner) {
    if (item == null) return

    @Suppress("UNCHECKED_CAST")
    (adapter as? BaseSingleViewPagingAdapter<Any>)?.run {
        submitData(lifecycleOwner.lifecycle, item)
    }
}

class BaseSingleViewAdapter<ITEM : Any>(
    @LayoutRes private val layoutResourceId: Int,
    private val bindingItemId: Int,
    private val viewModel: Map<Int, Any>,
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : ListAdapter<ITEM, BaseViewHolder>(diffUtil) {

    var itemClickListener: ((item: Any, pos: Int) -> Unit)? = null

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

class BaseMultiViewAdapter<ITEM : Any>(
    private val viewHolderMapper: (ITEM) -> ViewHolderType,
    private val viewHolderType: KClass<out ViewHolderType>,
    private val viewModel: Map<Int, ViewModel>,
    diffUtil: DiffUtil.ItemCallback<ITEM>,
    private val hasStableIds: Boolean = false
) : ListAdapter<ITEM, BaseViewHolder>(diffUtil) {

    var itemClickListener: ((item: Any, pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val viewHolderType = viewHolderType.java.enumConstants[viewType]
        return BaseViewHolder(
            parent = parent,
            layoutResourceId = viewHolderType.layoutResourceId,
            bindingItemId = viewHolderType.bindingItemId,
            viewModel = viewModel
        )
    }

    override fun getItemViewType(position: Int): Int =
        (viewHolderMapper(getItem(position)) as Enum<*>).ordinal

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (itemClickListener == null) {
            holder.bind(getItem(position))
        } else {
            holder.bind(getItem(position), itemClickListener!!, position)
        }
    }

    override fun getItemId(position: Int): Long = if (hasStableIds) {
        position.toLong()
    } else {
        super.getItemId(position)
    }
}

interface ViewHolderType {
    val layoutResourceId: Int
    val bindingItemId: Int
}

class BaseSingleViewPagingAdapter<ITEM : Any>(
    @LayoutRes private val layoutResourceId: Int,
    private val bindingItemId: Int,
    private val viewModel: Map<Int, ViewModel>,
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : PagingDataAdapter<ITEM, BaseViewHolder>(diffUtil) {

    private var previousLoading: Boolean = false
    var itemClickListener: ((item: Any, pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(
            parent = parent,
            layoutResourceId = layoutResourceId,
            bindingItemId = bindingItemId,
            viewModel = viewModel
        )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position)?.let {
            if (itemClickListener == null) {
                holder.bind(it)
            } else {
                holder.bind(it, itemClickListener!!, position)
            }
        }
    }

    /**
     * @param onLoading : 새로고침 로딩 상태
     * @param onItemCount : 현재 리스트 사이즈
     * @param onError : 데이터 로드 에러
     */
    fun setupLoadStateListener(
        scope: CoroutineScope,
        onLoading: (Boolean) -> Unit = {},
        onItemCount: (Int) -> Unit = {},
        onError: (String?) -> Unit = {}
    ) {
        scope.launch {
            loadStateFlow
                .dropWhile {
                    it.refresh is LoadState.NotLoading &&
                        it.append is LoadState.NotLoading &&
                        it.prepend is LoadState.NotLoading
                }
                .distinctUntilChanged()
                .collect {
                    val loading = it.refresh is LoadState.Loading
                    if (previousLoading != loading) {
                        onLoading(loading)
                        previousLoading = loading
                    }

                    if (!loading) onItemCount(itemCount)

                    val errorState = it.append as? LoadState.Error
                        ?: it.prepend as? LoadState.Error
                        ?: it.refresh as? LoadState.Error

                    errorState?.error?.message.let(onError)
                }
        }
    }
}
