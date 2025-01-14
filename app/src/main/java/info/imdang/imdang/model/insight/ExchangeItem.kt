package info.imdang.imdang.model.insight

sealed class ExchangeItem {

    data class Pass(
        val count: Int,
        val isSelected: Boolean = false
    ) : ExchangeItem()

    data class Insight(
        val insightVo: InsightVo,
        val isSelected: Boolean = false
    ) : ExchangeItem()
}
