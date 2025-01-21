package info.imdang.imdang.ui.main.storage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
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
import info.imdang.imdang.common.bindingadapter.BaseSingleViewPagingAdapter
import info.imdang.imdang.common.ext.dpToPx
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.FragmentStorageBinding
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.model.myinsight.MyInsightAddressVo
import info.imdang.imdang.ui.insight.InsightDetailActivity
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID
import info.imdang.imdang.ui.main.storage.address.InsightAddressActivity
import info.imdang.imdang.ui.main.storage.address.InsightAddressActivity.Companion.SELECTED_PAGE
import info.imdang.imdang.ui.main.storage.bottomsheet.ComplexBottomSheet
import info.imdang.imdang.ui.main.storage.map.StorageByMapActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StorageFragment : BaseFragment<FragmentStorageBinding>(R.layout.fragment_storage) {

    private val viewModel by viewModels<StorageViewModel>()

    private lateinit var adapter: BaseSingleViewPagingAdapter<InsightVo>

    private val addressResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.vpInsightAddress.currentItem = result.data?.getIntExtra(SELECTED_PAGE, 0)
                ?: viewModel.selectedInsightAddressPage.value
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
            viewModel = this@StorageFragment.viewModel
            rvStorageInsight.run {
                addItemDecoration(SpaceItemDecoration(space = 12))
                this@StorageFragment.adapter = BaseSingleViewPagingAdapter(
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
                ).apply {
                    itemClickListener = { item, _ ->
                        if (item is InsightVo) {
                            requireContext().startActivity<InsightDetailActivity>(
                                bundle = bundleOf(INSIGHT_ID to item.insightId)
                            )
                        }
                    }
                    setupLoadStateListener(
                        scope = lifecycleScope,
                        onLoading = {
                            this@StorageFragment.viewModel.updatePagingState(isLoading = it)
                        },
                        onItemCount = {
                            this@StorageFragment.viewModel.updatePagingState(itemCount = it)
                        },
                        onError = {
                            this@StorageFragment.viewModel.updatePagingState(error = it)
                        }
                    )
                }
                adapter = this@StorageFragment.adapter
            }
            vpInsightAddress.run {
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
                    layoutResourceId = R.layout.page_insight_address,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@StorageFragment.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<MyInsightAddressVo>() {
                        override fun areItemsTheSame(
                            oldItem: MyInsightAddressVo,
                            newItem: MyInsightAddressVo
                        ): Boolean = oldItem.toSiGuDong() == newItem.toSiGuDong()

                        override fun areContentsTheSame(
                            oldItem: MyInsightAddressVo,
                            newItem: MyInsightAddressVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                )
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            clStorageMap.setOnClickListener {
                requireContext().startActivity<StorageByMapActivity>()
            }
            tvInsightAddressSeeAll.setOnClickListener {
                addressResult.launch(
                    Intent(requireContext(), InsightAddressActivity::class.java).apply {
                        putExtra(
                            SELECTED_PAGE,
                            this@StorageFragment.viewModel.selectedInsightAddressPage.value
                        )
                    }
                )
            }
            vpInsightAddress.registerOnPageChangeCallback(
                object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        this@StorageFragment.viewModel.selectInsightAddressPage(position)
                    }
                }
            )
            tvSeeByAptAll.setOnClickListener {
                this@StorageFragment.viewModel.updateSelectedComplex(null)
            }
            clSeeByApt.setOnClickListener {
                showAptBottomSheet()
            }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    is StorageEvent.UpdateInsights -> launch {
                        adapter.submitData(it.insights)
                    }
                }
            }
        }
    }

    private fun showAptBottomSheet() {
        ComplexBottomSheet.instance(viewModel).show(
            requireActivity().supportFragmentManager,
            ComplexBottomSheet::class.java.simpleName
        )
    }
}
