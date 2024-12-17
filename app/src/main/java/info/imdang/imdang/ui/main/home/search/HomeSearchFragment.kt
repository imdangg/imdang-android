package info.imdang.imdang.ui.main.home.search

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.FragmentHomeSearchBinding
import info.imdang.imdang.model.insight.InsightAptVo
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.main.home.search.recommend.RecommendInsightsListener
import info.imdang.imdang.ui.main.home.search.recommend.RecommendInsightsPagerAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeSearchFragment : BaseFragment<FragmentHomeSearchBinding>(R.layout.fragment_home_search) {

    private val viewModel by viewModels<HomeSearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@HomeSearchFragment.viewModel
            rvHomeMyInsightApt.run {
                addItemDecoration(SpaceItemDecoration(space = 8))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_home_my_insight_apt,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@HomeSearchFragment.viewModel),
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
            rvHomeMyInsight.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_insight_horizontal,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@HomeSearchFragment.viewModel),
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
                        requireContext().startActivity<InsightDetailActivity>()
                    }
                }
            }
            rvHomeNewInsight.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_insight_vertical,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@HomeSearchFragment.viewModel),
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
                        requireContext().startActivity<InsightDetailActivity>()
                    }
                }
            }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.recommendInsights.collect {
                if (it.isNotEmpty()) setupViewPager()
            }
        }
    }

    private fun setupViewPager() {
        binding.vpHomeRecommendInsight.run {
            adapter = RecommendInsightsPagerAdapter(
                fragmentActivity = requireActivity(),
                insights = this@HomeSearchFragment.viewModel.recommendInsights.value,
                listener = object : RecommendInsightsListener {
                    override fun onClickInsight(insightVo: InsightVo) {
                        requireContext().startActivity<InsightDetailActivity>()
                    }
                }
            )
            TabLayoutMediator(binding.tlIndicator, this) { _, _ -> }.attach()
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.selectRecommendInsightPage(position)
                }
            })
        }
    }

    companion object {
        fun instance(): HomeSearchFragment = HomeSearchFragment()
    }
}
