package info.imdang.imdang.ui.main.home.search.visitcomplex

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityVisitComplexInsightListBinding
import info.imdang.imdang.model.insight.InsightAptVo
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity

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
                    layoutResourceId = R.layout.item_visit_complex_insight_apt,
                    bindingItemId = BR.item,
                    viewModel = emptyMap(),
                    diffUtil = object : DiffUtil.ItemCallback<InsightAptVo>() {
                        override fun areItemsTheSame(
                            oldItem: InsightAptVo,
                            newItem: InsightAptVo
                        ): Boolean = oldItem.aptName == newItem.aptName

                        override fun areContentsTheSame(
                            oldItem: InsightAptVo,
                            newItem: InsightAptVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                )
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
                    itemClickListener = { _, _ ->
                        startActivity<InsightDetailActivity>()
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
