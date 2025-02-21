package info.imdang.imdang.ui.main.home.history

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewPagingAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.common.util.logEvent
import info.imdang.imdang.databinding.FragmentHomeHistoryRequestedBinding
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID
import info.imdang.imdang.ui.main.home.exchange.HomeExchangeEvent
import info.imdang.imdang.ui.main.home.exchange.HomeExchangeViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeHistoryRequestedFragment :
    BaseFragment<FragmentHomeHistoryRequestedBinding>(R.layout.fragment_home_history_requested) {

    private val viewModel: HomeExchangeViewModel by viewModels()

    private var type: ExchangeType = ExchangeType.REQUESTED

    private lateinit var adapter: BaseSingleViewPagingAdapter<InsightVo>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateExchangeType(type)
        setupBinding()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@HomeHistoryRequestedFragment.viewModel

            rvHomeHistoryRequested.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                this@HomeHistoryRequestedFragment.adapter = BaseSingleViewPagingAdapter(
                    layoutResourceId = R.layout.item_insight_horizontal,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@HomeHistoryRequestedFragment.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<InsightVo>() {
                        override fun areItemsTheSame(
                            oldItem: InsightVo,
                            newItem: InsightVo
                        ): Boolean = oldItem.title == newItem.title

                        override fun areContentsTheSame(
                            oldItem: InsightVo,
                            newItem: InsightVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                ).apply {
                    itemClickListener = { item, _ ->
                        val event = when (type) {
                            ExchangeType.REQUESTED -> "내가 요청한 내역(인사이트)"
                            ExchangeType.RECEIVED -> "요청 받은 내역(인사이트)"
                        }
                        val action = when (type) {
                            ExchangeType.REQUESTED -> "요청한내역_인사이트_click"
                            ExchangeType.RECEIVED -> "요청받은내역_인사이트_click"
                        }
                        if (item is InsightVo) {
                            logEvent(
                                event = event,
                                category = "홈_교환소",
                                action = action,
                                label = item.title
                            )
                            requireContext().startActivity<InsightDetailActivity>(
                                bundle = bundleOf(INSIGHT_ID to item.insightId)
                            )
                        }
                    }
                    when (type) {
                        ExchangeType.REQUESTED -> {
                            setupLoadStateListener(
                                scope = lifecycleScope,
                                onLoading = {
                                    this@HomeHistoryRequestedFragment.viewModel.updatePagingState(
                                        isLoading = it
                                    )
                                },
                                onError = {
                                    this@HomeHistoryRequestedFragment.viewModel.updatePagingState(
                                        error = it
                                    )
                                }
                            )
                        }

                        ExchangeType.RECEIVED -> {
                            setupLoadStateListener(
                                scope = lifecycleScope,
                                onLoading = {
                                    this@HomeHistoryRequestedFragment.viewModel.updatePagingState(
                                        isLoading = it
                                    )
                                },
                                onError = {
                                    this@HomeHistoryRequestedFragment.viewModel.updatePagingState(
                                        error = it
                                    )
                                }
                            )
                        }
                    }
                }
                adapter = this@HomeHistoryRequestedFragment.adapter
            }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            when (type) {
                ExchangeType.REQUESTED -> {
                    viewModel.event.collect { event ->
                        if (event is HomeExchangeEvent.UpdateMyExchanges) {
                            adapter.submitData(lifecycle, event.exchanges)
                        }
                    }
                }

                ExchangeType.RECEIVED -> {
                    viewModel.event.collect { event ->
                        if (event is HomeExchangeEvent.UpdateOthersExchanges) {
                            adapter.submitData(lifecycle, event.exchanges)
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun instance(type: ExchangeType): HomeHistoryRequestedFragment {
            return HomeHistoryRequestedFragment().apply {
                this.type = type
            }
        }
    }
}

enum class ExchangeType {
    REQUESTED,
    RECEIVED
}
