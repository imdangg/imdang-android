package info.imdang.imdang.ui.my

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.my.MyInfoVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor() : BaseViewModel() {

    private val _myInfo = MutableStateFlow(MyInfoVo.getSample())
    val myInfo = _myInfo.asStateFlow()
}
