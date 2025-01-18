package info.imdang.imdang.ui.main.home.search.visitcomplex

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityVisitComplexInsightListBinding
import info.imdang.imdang.model.aptcomplex.VisitedAptComplexVo
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID

@AndroidEntryPoint
class VisitComplexInsightListActivity : BaseActivity<ActivityVisitComplexInsightListBinding>(
    R.layout.activity_visit_complex_insight_list
) {

    private val viewModel by viewModels<VisitComplexInsightListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
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
                    diffUtil = object : DiffUtil.ItemCallback<VisitedAptComplexVo>() {
                        override fun areItemsTheSame(
                            oldItem: VisitedAptComplexVo,
                            newItem: VisitedAptComplexVo
                        ): Boolean = oldItem.name == newItem.name

                        override fun areContentsTheSame(
                            oldItem: VisitedAptComplexVo,
                            newItem: VisitedAptComplexVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                ).apply {
                    itemClickListener = { item, _ ->
                        if (item is VisitedAptComplexVo) {
                            this@VisitComplexInsightListActivity.viewModel.onClickVisitedAptComplex(
                                item
                            )
                        }
                    }
                }
            }
            rvVisitComplexInsight.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                adapter = BaseSingleViewAdapter(
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
                }
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
}
