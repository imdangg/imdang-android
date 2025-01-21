package info.imdang.imdang.ui.main.storage

import androidx.paging.PagingData
import info.imdang.imdang.model.insight.InsightVo

sealed class StorageEvent {

    data class UpdateInsights(val insights: PagingData<InsightVo>) : StorageEvent()
}
