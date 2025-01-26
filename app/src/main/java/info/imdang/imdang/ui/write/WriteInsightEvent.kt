package info.imdang.imdang.ui.write

sealed class WriteInsightEvent {

    data class ShowToast(val message: String) : WriteInsightEvent()

    data class WriteInsightComplete(val insightId: String) : WriteInsightEvent()
}
