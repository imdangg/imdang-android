package info.imdang.imdang.ui.main.home.search.region

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.usecase.district.GetEupMyeonDongParams
import info.imdang.domain.usecase.district.GetEupMyeonDongUseCase
import info.imdang.domain.usecase.district.GetSiGunGuUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.district.DistrictVo
import info.imdang.imdang.model.district.mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchByRegionViewModel @Inject constructor(
    private val getSiGunGuUseCase: GetSiGunGuUseCase,
    private val getEupMyeonDongUseCase: GetEupMyeonDongUseCase
) : BaseViewModel() {

    private val _guList = MutableStateFlow<List<DistrictVo>>(emptyList())
    val guList = _guList.asStateFlow()

    private val _dongList = MutableStateFlow<List<String>>(emptyList())
    val dongList = _dongList.asStateFlow()

    init {
        fetchSiGunGu()
    }

    private fun fetchSiGunGu() {
        viewModelScope.launch {
            _guList.value = getSiGunGuUseCase(parameters = PagingParams(size = 30))
                ?.content
                ?.map(DistrictDto::mapper)
                ?.sortedBy { it.siGunGu } ?: emptyList()
            selectGu(0)
        }
    }

    fun selectGu(selectedIndex: Int) {
        _guList.value = guList.value.mapIndexed { index, regionVo ->
            regionVo.copy(isSelected = index == selectedIndex)
        }
        fetchEupMyeonDong()
    }

    private fun fetchEupMyeonDong() {
        viewModelScope.launch {
            _dongList.value = getEupMyeonDongUseCase(
                parameters = GetEupMyeonDongParams(
                    siGunGu = getSelectedGu(),
                    pagingParams = PagingParams(size = 30)
                )
            )?.content
                ?.map { it.eupMyeonDong ?: "" }
                ?.sortedBy { it } ?: emptyList()
        }
    }

    fun getSelectedGu(): String = guList.value.first { it.isSelected }.siGunGu
}
