package info.imdang.imdang.ui.main.home.search.visitcomplex

import androidx.paging.PagingData
import info.imdang.imdang.model.insight.InsightVo

sealed class VisitComplexInsightListEvent {

    data class ScrollToSelectedPosition(val position: Int) : VisitComplexInsightListEvent()

    data class UpdateInsights(val insights: PagingData<InsightVo>) : VisitComplexInsightListEvent()
}
