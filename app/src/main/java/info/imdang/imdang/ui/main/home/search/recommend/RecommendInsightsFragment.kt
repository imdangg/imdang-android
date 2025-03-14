package info.imdang.imdang.ui.main.home.search.recommend

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.FragmentRecommendInsightsBinding
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class RecommendInsightsFragment :
    BaseFragment<FragmentRecommendInsightsBinding>(R.layout.fragment_recommend_insights) {

    private val viewModel by viewModels<RecommendInsightsViewModel>()

    private lateinit var listener: RecommendInsightsListener

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
                    viewModel = mapOf(BR.viewModel to viewModel),
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
            }
        }
    }

    companion object {
        const val INSIGHTS = "insights"

        fun instance(
            insights: List<InsightVo>,
            listener: RecommendInsightsListener
        ): RecommendInsightsFragment =
            RecommendInsightsFragment().apply {
                arguments = bundleOf(INSIGHTS to insights)
                this.listener = listener
            }
    }
}

interface RecommendInsightsListener : Serializable {

    fun onClickInsight(insightVo: InsightVo)
}
