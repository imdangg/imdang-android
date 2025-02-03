package info.imdang.imdang.ui.main.home.search.region.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewPagingAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityInsightListBinding
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InsightListActivity : BaseActivity<ActivityInsightListBinding>(
    R.layout.activity_insight_list
) {

    private val viewModel by viewModels<InsightListViewModel>()

    private lateinit var adapter: BaseSingleViewPagingAdapter<InsightVo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("지역으로 찾기_상세")
        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@InsightListActivity.viewModel
            rvInsight.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                this@InsightListActivity.adapter = BaseSingleViewPagingAdapter(
                    layoutResourceId = R.layout.item_insight_horizontal,
                    bindingItemId = BR.item,
                    viewModel = emptyMap(),
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
                            startActivity<InsightDetailActivity>(
                                bundle = bundleOf(INSIGHT_ID to item.insightId)
                            )
                        }
                    }
                    setupLoadStateListener(
                        scope = lifecycleScope,
                        onLoading = {
                            this@InsightListActivity.viewModel.updatePagingState(isLoading = it)
                        },
                        onError = {
                            this@InsightListActivity.viewModel.updatePagingState(error = it)
                        }
                    )
                }
                adapter = this@InsightListActivity.adapter
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
                    is InsightListEvent.UpdateInsights -> launch {
                        adapter.submitData(it.insights)
                    }
                }
            }
        }
    }
}
