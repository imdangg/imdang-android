package info.imdang.imdang.ui.main.home.search.region

import androidx.paging.PagingData

sealed class SearchByRegionEvent {

    data class UpdateInsights(val insights: PagingData<String>) : SearchByRegionEvent()

    data object ClearData : SearchByRegionEvent()
}
