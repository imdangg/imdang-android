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
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.FragmentHomeHistoryRequestedBinding
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID
import info.imdang.imdang.ui.main.home.exchange.HomeExchangeViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeHistoryRequestedFragment :
    BaseFragment<FragmentHomeHistoryRequestedBinding>(R.layout.fragment_home_history_requested) {

    private val viewModel: HomeExchangeViewModel by viewModels()

    private var type: ExchangeType = ExchangeType.REQUESTED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateExchangeType(type)
        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@HomeHistoryRequestedFragment.viewModel

            rvHomeHistoryRequested.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                adapter = BaseSingleViewAdapter(
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
                        if (item is InsightVo) {
                            requireContext().startActivity<InsightDetailActivity>(
                                bundle = bundleOf(INSIGHT_ID to item.insightId)
                            )
                        }
                    }
                }

                val dataObserve = when (type) {
                    ExchangeType.REQUESTED -> {
                        this@HomeHistoryRequestedFragment.viewModel.myExchanges
                    }

                    ExchangeType.RECEIVED -> {
                        this@HomeHistoryRequestedFragment.viewModel.othersExchanges
                    }
                }

                lifecycleScope.launch {
                    dataObserve.collect { items ->
                        (adapter as BaseSingleViewAdapter<InsightVo>).submitList(items)
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
