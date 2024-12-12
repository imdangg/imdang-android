package info.imdang.imdang.ui.main.storage

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.dpToPx
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.FragmentStorageBinding
import info.imdang.imdang.model.insight.InsightRegionVo
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.main.storage.bottomsheet.AptBottomSheet
import info.imdang.imdang.ui.main.storage.region.InsightRegionActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StorageFragment : BaseFragment<FragmentStorageBinding>(R.layout.fragment_storage) {

    private val viewModel by viewModels<StorageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupCollect()
        setupListener()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@StorageFragment.viewModel
            rvStorageInsight.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_insight_horizontal,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@StorageFragment.viewModel),
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
            vpInsightRegion.run {
                val currentVisibleItemPx = requireContext().dpToPx(20)
                val nextVisibleItemPx = requireContext().dpToPx(8)
                val offsetPx = nextVisibleItemPx + currentVisibleItemPx
                addItemDecoration(
                    object : RecyclerView.ItemDecoration() {
                        override fun getItemOffsets(
                            outRect: Rect,
                            view: View,
                            parent: RecyclerView,
                            state: RecyclerView.State
                        ) {
                            outRect.right = currentVisibleItemPx
                            outRect.left = currentVisibleItemPx
                        }
                    }
                )
                setPageTransformer { page, position ->
                    page.translationX = position * -offsetPx
                }
                offscreenPageLimit = 1
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.page_insight_region,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@StorageFragment.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<InsightRegionVo>() {
                        override fun areItemsTheSame(
                            oldItem: InsightRegionVo,
                            newItem: InsightRegionVo
                        ): Boolean = oldItem.region == newItem.region

                        override fun areContentsTheSame(
                            oldItem: InsightRegionVo,
                            newItem: InsightRegionVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                )
            }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    is StorageEvent.OnClickInsight -> {
                        requireContext().startActivity<InsightDetailActivity>()
                    }
                }
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            tvInsightRegionSeeAll.setOnClickListener {
                requireContext().startActivity<InsightRegionActivity>()
            }
            vpInsightRegion.registerOnPageChangeCallback(
                object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        this@StorageFragment.viewModel.selectInsightRegionPage(position)
                    }
                }
            )
            clSeeByApt.setOnClickListener {
                showAptBottomSheet()
            }
        }
    }

    private fun showAptBottomSheet() {
        AptBottomSheet.instance().show(
            requireActivity().supportFragmentManager,
            AptBottomSheet::class.java.simpleName
        )
    }
}
