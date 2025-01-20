package info.imdang.imdang.ui.main.home.search.newinsight

import androidx.paging.PagingData
import info.imdang.imdang.model.insight.InsightVo

sealed class NewInsightListEvent {

    data class UpdateInsights(val insights: PagingData<InsightVo>) : NewInsightListEvent()
}
