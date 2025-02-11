package info.imdang.imdang.ui.main.home.notification

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.bindingadapter.BaseMultiViewAdapter
import info.imdang.imdang.common.bindingadapter.ViewHolderType
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityNotificationBinding
import info.imdang.imdang.model.notification.NotificationItem
import info.imdang.imdang.ui.insight.InsightDetailActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationActivity :
    BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {

    private val viewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("알림")
        setupBinding()
        setupListener()
        setupCollect()
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
                            is NotificationItem.Empty -> NotificationItemHolderType.EmptyHolder
                        }
                    },
                    viewHolderType = NotificationItemHolderType::class,
                    viewModel = mapOf(BR.viewModel to this@NotificationActivity.viewModel),
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

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    NotificationEvent.MoveInsightDetailActivity -> {
                        startActivity<InsightDetailActivity>()
                    }
                    NotificationEvent.MoveStorage -> {
                        setResult(RESULT_STORAGE)
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        const val RESULT_STORAGE = 1001
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
        ),
        EmptyHolder(
            layoutResourceId = R.layout.item_notification_empty,
            bindingItemId = BR.item
        )
    }
}
