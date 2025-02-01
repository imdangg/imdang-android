package info.imdang.imdang.ui.main.home.search.region.list

import androidx.paging.PagingData
import info.imdang.imdang.model.insight.InsightVo

sealed class InsightListEvent {

    data class UpdateInsights(val insights: PagingData<InsightVo>) : InsightListEvent()
}
