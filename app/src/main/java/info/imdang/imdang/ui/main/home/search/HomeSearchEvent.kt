package info.imdang.imdang.ui.main.home.search

import info.imdang.imdang.model.insight.InsightVo

sealed class HomeSearchEvent {

    data class OnClickInsight(val insightVo: InsightVo) : HomeSearchEvent()
}
