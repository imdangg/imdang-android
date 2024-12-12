package info.imdang.imdang.ui.main.home.search.recommend

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.main.home.search.HomeSearchEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendInsightsViewModel @Inject constructor() : BaseViewModel() {

    private val _event = MutableSharedFlow<HomeSearchEvent>()
    val event = _event.asSharedFlow()

    override fun <T> onClickItem(item: T) {
        if (item is InsightVo) {
            viewModelScope.launch {
                _event.emit(HomeSearchEvent.OnClickInsight(item))
            }
        }
    }
}
