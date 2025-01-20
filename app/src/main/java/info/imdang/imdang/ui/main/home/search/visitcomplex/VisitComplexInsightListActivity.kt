package info.imdang.imdang.ui.main.home.search.visitcomplex

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
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.bindingadapter.BaseSingleViewPagingAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityVisitComplexInsightListBinding
import info.imdang.imdang.model.aptcomplex.VisitAptComplexVo
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VisitComplexInsightListActivity : BaseActivity<ActivityVisitComplexInsightListBinding>(
    R.layout.activity_visit_complex_insight_list
) {

    private val viewModel by viewModels<VisitComplexInsightListViewModel>()

    private lateinit var adapter: BaseSingleViewPagingAdapter<InsightVo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@VisitComplexInsightListActivity.viewModel
            rvVisitComplexInsightApt.run {
                addItemDecoration(SpaceItemDecoration(space = 8))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_visited_apt_complex,
                    bindingItemId = BR.item,
                    viewModel = emptyMap(),
                    diffUtil = object : DiffUtil.ItemCallback<VisitAptComplexVo>() {
                        override fun areItemsTheSame(
                            oldItem: VisitAptComplexVo,
                            newItem: VisitAptComplexVo
                        ): Boolean = oldItem.aptComplexName == newItem.aptComplexName

                        override fun areContentsTheSame(
                            oldItem: VisitAptComplexVo,
                            newItem: VisitAptComplexVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                ).apply {
                    itemClickListener = { item, _ ->
                        if (item is VisitAptComplexVo) {
                            this@VisitComplexInsightListActivity.viewModel.onClickVisitedAptComplex(
                                item
                            )
                        }
                    }
                }
            }
            rvVisitComplexInsight.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                this@VisitComplexInsightListActivity.adapter = BaseSingleViewPagingAdapter(
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
                            this@VisitComplexInsightListActivity.viewModel.updatePagingState(
                                isLoading = it
                            )
                        },
                        onItemCount = {
                            this@VisitComplexInsightListActivity.viewModel.updatePagingState(
                                itemCount = it
                            )
                        },
                        onError = {
                            this@VisitComplexInsightListActivity.viewModel.updatePagingState(
                                error = it
                            )
                        }
                    )
                }
                adapter = this@VisitComplexInsightListActivity.adapter
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
                    is VisitComplexInsightListEvent.UpdateInsights -> launch {
                        adapter.submitData(it.insights)
                    }
                }
            }
        }
    }
}
