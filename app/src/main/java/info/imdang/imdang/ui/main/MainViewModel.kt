package info.imdang.imdang.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.user.GetUsersParams
import info.imdang.domain.usecase.user.GetUsersUseCase
import info.imdang.imdang.model.UserVo
import info.imdang.imdang.model.mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _users = MutableStateFlow(UserVo.init())
    val users = _users.asStateFlow()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.value = getUsersUseCase(
                parameters = GetUsersParams(q = "imaec")
            )?.mapper() ?: UserVo.init()
        }
    }
}
