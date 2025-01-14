package info.imdang.imdang.ui.insight

sealed class InsightDetailEvent {

    data object ShowMyInsightsBottomSheet : InsightDetailEvent()

    data class ShowCommonDialog(
        val dialogType: InsightDetailDialogType,
        val onClickSubButton: () -> Unit = {}
    ) : InsightDetailEvent()

    data object MoveHomeExchange : InsightDetailEvent()

    data object MoveStorage : InsightDetailEvent()
}
