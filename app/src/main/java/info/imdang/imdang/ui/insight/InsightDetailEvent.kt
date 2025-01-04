package info.imdang.imdang.ui.insight

sealed class InsightDetailEvent {

    data object ShowMyInsightsBottomSheet : InsightDetailEvent()

    data class ShowExchangeDialog(
        val message: String,
        val checkButtonText: String? = null
    ) : InsightDetailEvent()
}
