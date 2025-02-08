package info.imdang.imdang.ui.main.home.search.region

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.bindingadapter.BaseSingleViewPagingAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivitySearchByRegionBinding
import info.imdang.imdang.model.district.DistrictVo
import info.imdang.imdang.ui.main.home.search.map.SearchByMapActivity
import info.imdang.imdang.ui.main.home.search.region.list.InsightListActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchByRegionActivity : BaseActivity<ActivitySearchByRegionBinding>(
    R.layout.activity_search_by_region
) {

    private val viewModel by viewModels<SearchByRegionViewModel>()

    private lateinit var adapter: BaseSingleViewPagingAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@SearchByRegionActivity.viewModel
            rvGu.run {
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_region_gu,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@SearchByRegionActivity.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<DistrictVo>() {
                        override fun areItemsTheSame(
                            oldItem: DistrictVo,
                            newItem: DistrictVo
                        ): Boolean = oldItem.code == newItem.code

                        override fun areContentsTheSame(
                            oldItem: DistrictVo,
                            newItem: DistrictVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                ).apply {
                    itemClickListener = { item, index ->
                        if (item is DistrictVo) {
                            this@SearchByRegionActivity.viewModel.selectGu(index)
                        }
                    }
                }
            }
            rvDong.run {
                this@SearchByRegionActivity.adapter = BaseSingleViewPagingAdapter(
                    layoutResourceId = R.layout.item_region_dong,
                    bindingItemId = BR.item,
                    viewModel = emptyMap(),
                    diffUtil = object : DiffUtil.ItemCallback<String>() {
                        override fun areItemsTheSame(
                            oldItem: String,
                            newItem: String
                        ): Boolean = oldItem.hashCode() == newItem.hashCode()

                        override fun areContentsTheSame(
                            oldItem: String,
                            newItem: String
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                ).apply {
                    itemClickListener = { item, _ ->
                        if (item is String) {
                            this@SearchByRegionActivity.viewModel.getSelectedGu()?.let {
                                startActivity<InsightListActivity>(
                                    bundle = bundleOf(
                                        SI_GUN_GU to it,
                                        EUP_MYEON_DONG to item
                                    )
                                )
                            }
                        }
                    }
                    setupLoadStateListener(
                        scope = lifecycleScope,
                        onLoading = {
                            this@SearchByRegionActivity.viewModel.updatePagingState(isLoading = it)
                        },
                        onError = {
                            this@SearchByRegionActivity.viewModel.updatePagingState(error = it)
                        }
                    )
                }
                adapter = this@SearchByRegionActivity.adapter
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            clSearchByRegionMap.setOnClickListener {
                startActivity<SearchByMapActivity>()
            }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is SearchByRegionEvent.UpdateInsights -> {
                        adapter.submitData(event.insights)
                    }
                    SearchByRegionEvent.ClearData -> {
                        adapter.submitData(PagingData.empty())
                    }
                }
            }
        }
    }

    companion object {
        const val SI_GUN_GU = "siGunGu"
        const val EUP_MYEON_DONG = "eupMyeonDong"
    }
}
