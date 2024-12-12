package info.imdang.imdang.ui.main.home.exchange

import info.imdang.imdang.model.insight.InsightVo

sealed class HomeExchangeEvent {

    data class OnClickInsight(val insightVo: InsightVo) : HomeExchangeEvent()
}
