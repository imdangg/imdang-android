package info.imdang.imdang.ui.main.home.exchange

import androidx.paging.PagingData
import info.imdang.imdang.model.insight.InsightVo

sealed class HomeExchangeEvent {

    data class UpdateMyExchanges(val exchanges: PagingData<InsightVo>) : HomeExchangeEvent()

    data class UpdateOthersExchanges(val exchanges: PagingData<InsightVo>) : HomeExchangeEvent()
}
