package info.imdang.imdang.ui.main.home.notification

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.domain.usecase.notification.GetNotificationsParams
import info.imdang.domain.usecase.notification.GetNotificationsUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.notification.NotificationCategory
import info.imdang.imdang.model.notification.NotificationItem
import info.imdang.imdang.model.notification.NotificationListType
import info.imdang.imdang.model.notification.NotificationVo
import info.imdang.imdang.model.notification.mapper
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<NotificationEvent>()
    val event = _event.asSharedFlow()

    private val _selectedNotificationListType = MutableStateFlow(NotificationListType.ALL)
    val selectedNotificationListType = _selectedNotificationListType.asStateFlow()

    private val _newNotifications = MutableStateFlow<List<NotificationVo>>(emptyList())
    private val newNotifications = _newNotifications.asStateFlow()

    private val _lastNotifications = MutableStateFlow<List<NotificationVo>>(emptyList())
    private val lastNotifications = _lastNotifications.asStateFlow()

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications = _notifications.asStateFlow()

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            val notifications = awaitAll(
                async {
                    getNotificationsUseCase(
                        GetNotificationsParams(
                            isChecked = false,
                            pagingParams = PagingParams(size = 20)
                        )
                    )?.content?.map(NotificationDto::mapper) ?: emptyList()
                },
                async {
                    getNotificationsUseCase(
                        GetNotificationsParams(
                            isChecked = true,
                            pagingParams = PagingParams(size = 20)
                        )
                    )?.content?.map(NotificationDto::mapper) ?: emptyList()
                }
            )
            _newNotifications.value = notifications[0]
            _lastNotifications.value = notifications[1]
            _notifications.value = mutableListOf<NotificationItem>().apply {
                if (newNotifications.value.isEmpty() && lastNotifications.value.isEmpty()) {
                    add(NotificationItem.Empty(text = "최근 1년간 도착한 알림이 없어요"))
                    return@apply
                }
                add(NotificationItem.Title(title = "신규 알림"))
                if (newNotifications.value.isNotEmpty()) {
                    addAll(
                        newNotifications.value.map {
                            NotificationItem.Notification(it)
                        }
                    )
                } else {
                    add(NotificationItem.Empty(text = "오늘 도착한 신규 알림이 없어요"))
                }
                add(NotificationItem.Title(title = "지난 알림"))
                if (lastNotifications.value.isNotEmpty()) {
                    addAll(
                        lastNotifications.value.map {
                            NotificationItem.Notification(it)
                        }
                    )
                } else {
                    add(NotificationItem.Empty(text = "지난 알림이 없어요"))
                }
            }
        }
    }

    fun onClickNotificationType(notificationListType: NotificationListType) {
        _selectedNotificationListType.value = notificationListType
        fetchNotifications()
    }

    fun onClickActionButton(category: NotificationCategory) {
        viewModelScope.launch {
            when (category) {
                NotificationCategory.REQUESTED -> {
                    _event.emit(NotificationEvent.MoveInsightDetailActivity)
                }
                NotificationCategory.ACCEPTED -> {
                    _event.emit(NotificationEvent.MoveStorage)
                }
                NotificationCategory.REJECTED -> {
                    _event.emit(NotificationEvent.MoveInsightDetailActivity)
                }
            }
        }
    }
}
