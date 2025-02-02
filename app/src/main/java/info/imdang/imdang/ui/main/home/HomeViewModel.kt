package info.imdang.imdang.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.home.GetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.GetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.notification.HasNewNotificationUseCase
import info.imdang.imdang.common.util.diffDays
import info.imdang.imdang.common.util.isToday
import info.imdang.imdang.common.util.toLocalDate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val hasNewNotificationUseCase: HasNewNotificationUseCase,
    private val getFirstDateOfHomeFreePassUseCase: GetFirstDateOfHomeFreePassUseCase,
    private val setFirstDateOfHomeFreePassUseCase: SetFirstDateOfHomeFreePassUseCase,
    private val getCloseTimeOfHomeFreePassUseCase: GetCloseTimeOfHomeFreePassUseCase,
    private val setCloseTimeOfHomeFreePassUseCase: SetCloseTimeOfHomeFreePassUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _hasNewNotification = MutableStateFlow(false)
    val hasNewNotification = _hasNewNotification.asStateFlow()

    init {
        fetchHasNewNotification()
        fetchFreePassDateTime()
    }

    private fun fetchHasNewNotification() {
        viewModelScope.launch {
            _hasNewNotification.value = hasNewNotificationUseCase(Unit) ?: false
        }
    }

    private fun fetchFreePassDateTime() {
        viewModelScope.launch {
            val firstOpenDate = getFirstDateOfHomeFreePassUseCase(Unit)?.takeIf { it != 0L }
            if (firstOpenDate == null) {
                setFirstDateOfHomeFreePassUseCase(System.currentTimeMillis())
                _event.emit(HomeEvent.ShowHomeFreePassBottomSheet)
            } else if (firstOpenDate.diffDays() < 3) {
                val closeDate = (getCloseTimeOfHomeFreePassUseCase(Unit) ?: 0).toLocalDate()
                if (!closeDate.isToday()) {
                    _event.emit(HomeEvent.ShowHomeFreePassBottomSheet)
                }
            }
        }
    }

    fun setCloseTimeOfHomeFreePass() {
        viewModelScope.launch {
            setCloseTimeOfHomeFreePassUseCase(System.currentTimeMillis())
        }
    }
}
