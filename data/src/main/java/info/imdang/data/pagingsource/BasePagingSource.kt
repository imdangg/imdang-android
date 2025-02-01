package info.imdang.data.pagingsource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import info.imdang.domain.model.common.PagingDto

internal class BasePagingSource<R : Any>(
    private val initialPage: Int,
    private val loadData: suspend (Int) -> PagingDto<R>,
    private val totalCountListener: ((Int) -> Unit)? = null
) : PagingSource<Int, R>() {

    override fun getRefreshKey(state: PagingState<Int, R>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, R> {
        return try {
            val page = params.key ?: initialPage
            val nextPage = page + 1
            val prevPage = page - 1
            val paging = loadData(page)

            totalCountListener?.let { it(paging.totalElements) }
            LoadResult.Page(
                data = paging.content,
                prevKey = if (page == 0) null else prevPage,
                nextKey = if (!paging.last) nextPage else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

fun <R : Any> getPagingFlow(
    initialPage: Int,
    pageSize: Int,
    loadData: suspend (currentPage: Int, pageSize: Int) -> PagingDto<R>,
    totalCountListener: ((Int) -> Unit)? = null
) = Pager(
    config = PagingConfig(
        pageSize = pageSize,
        prefetchDistance = pageSize / 2
    ),
    pagingSourceFactory = {
        BasePagingSource(
            initialPage = initialPage,
            loadData = { loadData(it, pageSize) },
            totalCountListener = totalCountListener
        )
    }
).flow
