package info.imdang.imdang.ui.write

sealed class WriteInsightEvent {

    data class ShowToast(val message: String) : WriteInsightEvent()

    data object WriteInsightComplete : WriteInsightEvent()
}
