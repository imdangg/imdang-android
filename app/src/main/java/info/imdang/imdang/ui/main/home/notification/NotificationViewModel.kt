package info.imdang.imdang.ui.main.home.notification

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.domain.usecase.notification.GetNotificationsUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.notification.NotificationCategory
import info.imdang.imdang.model.notification.NotificationItem
import info.imdang.imdang.model.notification.NotificationListType
import info.imdang.imdang.model.notification.mapper
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

    private val _notificationItems = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notificationItems = _notificationItems.asStateFlow()

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            val notifications = getNotificationsUseCase(
                PagingParams(
                    size = 20,
                    properties = listOf("createdAt")
                )
            )
                ?.content
                ?.map(NotificationDto::mapper) ?: emptyList()
            val newNotifications = notifications.filter { it.isNewNotification }
            val lastNotifications = notifications.filter { !it.isNewNotification }

            _notificationItems.value = mutableListOf<NotificationItem>().apply {
                if (notifications.isEmpty()) {
                    add(NotificationItem.Empty(text = "최근 1년간 도착한 알림이 없어요"))
                    return@apply
                }
                add(NotificationItem.Title(title = "신규 알림"))
                if (notifications.isNotEmpty()) {
                    addAll(
                        newNotifications.map {
                            NotificationItem.Notification(it)
                        }
                    )
                } else {
                    add(NotificationItem.Empty(text = "오늘 도착한 신규 알림이 없어요"))
                }
                add(NotificationItem.Title(title = "지난 알림"))
                if (lastNotifications.isNotEmpty()) {
                    addAll(
                        lastNotifications.map {
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
                    // todo : 교환 수락
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

    fun onClickAction2Button(category: NotificationCategory) {
        if (category == NotificationCategory.REQUESTED) {
            // todo : 교환 거절
        }
    }
}
