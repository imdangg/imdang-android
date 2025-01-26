package info.imdang.imdang.base

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.launch

abstract class BasePagingDataAdapter<ITEM : Any, VH : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : PagingDataAdapter<ITEM, VH>(diffUtil) {

    private var previousLoading: Boolean? = null

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
