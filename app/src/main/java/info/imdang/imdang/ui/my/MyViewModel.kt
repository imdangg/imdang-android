package info.imdang.imdang.ui.my

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.my.MyInfoVo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val removeTokenUseCase: RemoveTokenUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<MyEvent>()
    val event = _event.asSharedFlow()

    private val _myInfo = MutableStateFlow(MyInfoVo.getSample())
    val myInfo = _myInfo.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            removeTokenUseCase(Unit)
            _event.emit(MyEvent.Logout)
        }
    }
}
