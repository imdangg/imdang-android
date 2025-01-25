package info.imdang.imdang.ui.main.home.notification

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.notification.NotificationItem
import info.imdang.imdang.model.notification.NotificationListType
import info.imdang.imdang.model.notification.NotificationType
import info.imdang.imdang.model.notification.NotificationVo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : BaseViewModel() {

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
        _newNotifications.value = NotificationVo.getNewSamples().filter {
            when (selectedNotificationListType.value) {
                NotificationListType.ALL -> true
                NotificationListType.REQUEST_HISTORY -> {
                    it.type == NotificationType.EXCHANGE_ACCEPT ||
                        it.type == NotificationType.EXCHANGE_REJECT
                }
                NotificationListType.REQUESTED_HISTORY -> {
                    it.type == NotificationType.EXCHANGE_REQUESTED
                }
            }
        }
        _lastNotifications.value = NotificationVo.getLastSamples().filter {
            when (selectedNotificationListType.value) {
                NotificationListType.ALL -> true
                NotificationListType.REQUEST_HISTORY -> {
                    it.type == NotificationType.EXCHANGE_ACCEPT ||
                        it.type == NotificationType.EXCHANGE_REJECT
                }
                NotificationListType.REQUESTED_HISTORY -> {
                    it.type == NotificationType.EXCHANGE_REQUESTED
                }
            }
        }
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

    fun onClickNotificationType(notificationListType: NotificationListType) {
        _selectedNotificationListType.value = notificationListType
        fetchNotifications()
    }

    fun onClickActionButton(notificationType: NotificationType) {
        viewModelScope.launch {
            when (notificationType) {
                NotificationType.EXCHANGE_REQUESTED -> {
                    _event.emit(NotificationEvent.MoveInsightDetailActivity)
                }
                NotificationType.EXCHANGE_ACCEPT -> {
                    _event.emit(NotificationEvent.MoveStorage)
                }
                NotificationType.EXCHANGE_REJECT -> {
                    _event.emit(NotificationEvent.MoveInsightDetailActivity)
                }
            }
        }
    }
}
