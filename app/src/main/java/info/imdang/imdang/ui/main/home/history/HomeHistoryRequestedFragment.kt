package info.imdang.imdang.ui.main.home.history

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.databinding.FragmentHomeHistoryRequestedBinding
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.main.home.exchange.HomeExchangeViewModel

@AndroidEntryPoint
class HomeHistoryRequestedFragment :
    BaseFragment<FragmentHomeHistoryRequestedBinding>(R.layout.fragment_home_history_requested) {

    private val viewModel: HomeExchangeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        initializeViewModel()
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
                )
            }
        }
    }

    private fun initializeViewModel() {
        viewModel.setChipDescriptions(
            listOf(
                getString(info.imdang.component.R.string.waiting_details_existence),
                getString(info.imdang.component.R.string.refusal_details_existence),
                getString(info.imdang.component.R.string.exchange_details_existence)
            )
        )
    }

    companion object {
        fun instance(): HomeHistoryRequestedFragment = HomeHistoryRequestedFragment()
    }
}
