package info.imdang.imdang.ui.main.home.search.region

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.address.RegionVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SearchByRegionViewModel @Inject constructor() : BaseViewModel() {

    private val _guList = MutableStateFlow<List<RegionVo>>(emptyList())
    val guList = _guList.asStateFlow()

    private val _dongList = MutableStateFlow<List<String>>(emptyList())
    val dongList = _dongList.asStateFlow()

    init {
        _guList.value = RegionVo.getSamples(10)
        _dongList.value = RegionVo.getDongSamples(5)
    }

    fun selectGu(selectedIndex: Int) {
        _guList.value = guList.value.mapIndexed { index, regionVo ->
            regionVo.copy(isSelected = index == selectedIndex)
        }
        _dongList.value = RegionVo.getDongSamples(Random.nextInt(10) + 1)
    }

    fun getSelectedGu(): String = guList.value.first { it.isSelected }.name
}
