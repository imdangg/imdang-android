package info.imdang.imdang.ui.main.home.notification

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.bindingadapter.BaseMultiViewAdapter
import info.imdang.imdang.common.bindingadapter.ViewHolderType
import info.imdang.imdang.databinding.ActivityNotificationBinding
import info.imdang.imdang.model.notification.NotificationItem

@AndroidEntryPoint
class NotificationActivity :
    BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {

    private val viewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@NotificationActivity.viewModel
            with(rvNotification) {
                addItemDecoration(NotificationItemDecoration())
                adapter = BaseMultiViewAdapter(
                    viewHolderMapper = {
                        when (it) {
                            is NotificationItem.Title -> NotificationItemHolderType.TitleHolder
                            is NotificationItem.Notification -> {
                                NotificationItemHolderType.NotificationHolder
                            }
                        }
                    },
                    viewHolderType = NotificationItemHolderType::class,
                    viewModel = emptyMap(),
                    diffUtil = object : DiffUtil.ItemCallback<NotificationItem>() {
                        override fun areItemsTheSame(
                            oldItem: NotificationItem,
                            newItem: NotificationItem
                        ): Boolean = oldItem == newItem

                        override fun areContentsTheSame(
                            oldItem: NotificationItem,
                            newItem: NotificationItem
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                )
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    enum class NotificationItemHolderType(
        override val layoutResourceId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        TitleHolder(
            layoutResourceId = R.layout.item_notification_title,
            bindingItemId = BR.item
        ),
        NotificationHolder(
            layoutResourceId = R.layout.item_notification,
            bindingItemId = BR.item
        )
    }
}
