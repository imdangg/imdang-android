package info.imdang.imdang.ui.main.storage

import info.imdang.imdang.model.insight.InsightVo

sealed class StorageEvent {

    data class OnClickInsight(val insightVo: InsightVo) : StorageEvent()
}
