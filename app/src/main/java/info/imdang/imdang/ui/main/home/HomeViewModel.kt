package info.imdang.imdang.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import info.imdang.domain.usecase.home.GetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetCloseTimeOfHomeFreePassUseCase
import info.imdang.imdang.common.util.isToday
import info.imdang.imdang.common.util.toLocalDate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCloseTimeOfHomeFreePassUseCase: GetCloseTimeOfHomeFreePassUseCase,
    private val setCloseTimeOfHomeFreePassUseCase: SetCloseTimeOfHomeFreePassUseCase,
    private val removeTokenUseCase: RemoveTokenUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val closeDate = (getCloseTimeOfHomeFreePassUseCase(Unit) ?: 0).toLocalDate()
            if (!closeDate.isToday()) {
                _event.emit(HomeEvent.ShowHomeFreePassBottomSheet)
            }
        }
    }

    fun setCloseTimeOfHomeFreePass() {
        viewModelScope.launch {
            setCloseTimeOfHomeFreePassUseCase(System.currentTimeMillis())
        }
    }

    fun logout() {
        viewModelScope.launch {
            removeTokenUseCase(Unit)
            _event.emit(HomeEvent.Logout)
        }
    }
}
