package info.imdang.imdang.ui.main.home.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.databinding.FragmentRecommendInsightsBinding
import info.imdang.imdang.model.insight.InsightVo

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class RecommendInsightsFragment :
    BaseFragment<FragmentRecommendInsightsBinding>(R.layout.fragment_recommend_insights) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            insights = arguments?.getSerializable(INSIGHTS) as List<InsightVo>
            rvHomeRecommendInsight.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_insight_horizontal,
                    bindingItemId = BR.item,
                    viewModel = mapOf(),
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

    companion object {
        const val INSIGHTS = "insights"

        fun instance(insights: List<InsightVo>): RecommendInsightsFragment =
            RecommendInsightsFragment().apply {
                arguments = bundleOf(INSIGHTS to insights)
            }
    }
}