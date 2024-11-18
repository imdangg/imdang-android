package info.imdang.imdang.ui.main.home.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.model.insight.InsightAptVo
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeSearchViewModel @Inject constructor() : ViewModel() {

    private val _myInsightApts = MutableStateFlow<List<InsightAptVo>>(emptyList())
    val myInsightApts = _myInsightApts.asStateFlow()

    private val _myInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val myInsights = _myInsights.asStateFlow()

    private val _newInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val newInsights = _newInsights.asStateFlow()

    private val _recommendInsights = MutableStateFlow<List<List<InsightVo>>>(emptyList())
    val recommendInsights = _recommendInsights.asStateFlow()

    private val _selectedRecommendInsightPage = MutableStateFlow(1)
    val selectedRecommendInsightPage = _selectedRecommendInsightPage.asStateFlow()

    private val _isVisibleShadow = MutableStateFlow(false)
    val isVisibleShadow = _isVisibleShadow.asStateFlow()

    init {
        _myInsightApts.value = listOf(
            InsightAptVo(
                aptName = "신논현 더 센트럴 푸르지오",
                isSelected = true
            ),
            InsightAptVo(
                aptName = "신논현 더 센트럴 푸르지오",
                isSelected = false
            ),
            InsightAptVo(
                aptName = "신논현 더 센트럴 푸르지오",
                isSelected = false
            )
        )
        _myInsights.value = InsightVo.getSamples(size = 3)
        _newInsights.value = InsightVo.getSamples(size = 6)
        _recommendInsights.value = listOf(
            InsightVo.getSamples(3),
            InsightVo.getSamples(3),
            InsightVo.getSamples(3),
            InsightVo.getSamples(1)
        )
    }

    fun selectRecommendInsightPage(page: Int) {
        _selectedRecommendInsightPage.value = page + 1
    }

    fun toggleVisibleShadow(isVisible: Boolean) {
        if (isVisibleShadow.value != isVisible) _isVisibleShadow.value = isVisible
    }
}
