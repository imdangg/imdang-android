package info.imdang.imdang.ui.main.home.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.FragmentHomeSearchBinding
import info.imdang.imdang.model.aptcomplex.VisitAptComplexVo
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID
import info.imdang.imdang.ui.main.MainViewModel
import info.imdang.imdang.ui.main.home.search.map.SearchByMapActivity
import info.imdang.imdang.ui.main.home.search.newinsight.NewInsightListActivity
import info.imdang.imdang.ui.main.home.search.recommend.RecommendInsightsListener
import info.imdang.imdang.ui.main.home.search.recommend.RecommendInsightsPagerAdapter
import info.imdang.imdang.ui.main.home.search.region.SearchByRegionActivity
import info.imdang.imdang.ui.main.home.search.visitcomplex.VisitComplexInsightListActivity
import info.imdang.imdang.ui.my.introduction.ServiceIntroductionActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeSearchFragment : BaseFragment<FragmentHomeSearchBinding>(R.layout.fragment_home_search) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<HomeSearchViewModel>()

    private val visitComplexInsightListResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedIndex = result.data?.getIntExtra(SELECTED_COMPLEX_INDEX, 0) ?: 0
            if (viewModel.getSelectedComplexIndex() != selectedIndex) {
                viewModel.onClickVisitedAptComplex(
                    viewModel.visitedAptComplexes.value[selectedIndex]
                )
                binding.rvHomeVisitedAptComplex.scrollToPosition(selectedIndex)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@HomeSearchFragment.viewModel
            clHomeBanner.setOnClickListener {
                requireContext().startActivity<ServiceIntroductionActivity>(
                    bundle = bundleOf(FOCUS_VIEW to true)
                )
            }
            rvHomeVisitedAptComplex.run {
                addItemDecoration(SpaceItemDecoration(space = 8))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_visited_apt_complex,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@HomeSearchFragment.viewModel),
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
                            this@HomeSearchFragment.viewModel.onClickVisitedAptComplex(item)
                        }
                    }
                }
            }
            rvHomeVisitedAptComplexInsight.run {
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
                    itemClickListener = { item, _ ->
                        mainViewModel.hideTooltip()
                        if (item is InsightVo) {
                            requireContext().startActivity<InsightDetailActivity>(
                                bundle = bundleOf(INSIGHT_ID to item.insightId)
                            )
                        }
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
                    itemClickListener = { item, _ ->
                        mainViewModel.hideTooltip()
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

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.recommendInsights.collect {
                if (it.isNotEmpty()) setupViewPager()
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            clHomeSearch.setOnClickListener {
                requireContext().startActivity<SearchByRegionActivity>()
            }
            clHomeMap.setOnClickListener {
                requireContext().startActivity<SearchByMapActivity>()
            }
            nsvHomeSearch.setOnScrollChangeListener { _, _, _, _, _ ->
                mainViewModel.hideTooltip()
            }
            tvHomeVisitedAptComplexInsightSeeAll.setOnClickListener {
                val viewModel = this@HomeSearchFragment.viewModel
                if (viewModel.visitedAptComplexes.value.isNotEmpty()) {
                    mainViewModel.hideTooltip()
                    visitComplexInsightListResult.launch(
                        Intent(
                            requireContext(),
                            VisitComplexInsightListActivity::class.java
                        ).apply {
                            putExtra(
                                SELECTED_COMPLEX_INDEX,
                                viewModel.getSelectedComplexIndex()
                            )
                        }
                    )
                }
            }
            tvHomeNewInsightSeeAll.setOnClickListener {
                mainViewModel.hideTooltip()
                requireContext().startActivity<NewInsightListActivity>()
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
                        mainViewModel.hideTooltip()
                        requireContext().startActivity<InsightDetailActivity>(
                            bundle = bundleOf(INSIGHT_ID to insightVo.insightId)
                        )
                    }
                }
            )
            TabLayoutMediator(binding.tlIndicator, this) { _, _ -> }.attach()
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mainViewModel.hideTooltip()
                    viewModel.selectRecommendInsightPage(position)
                }
            })
        }
    }

    companion object {

        const val SELECTED_COMPLEX_INDEX = "selectedComplexIndex"
        const val FOCUS_VIEW = "focusView"

        fun instance(): HomeSearchFragment = HomeSearchFragment()
    }
}
