package info.imdang.imdang.ui.main.home.search.region

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivitySearchByRegionBinding
import info.imdang.imdang.model.address.RegionVo
import info.imdang.imdang.ui.main.home.search.map.SearchByMapActivity

@AndroidEntryPoint
class SearchByRegionActivity : BaseActivity<ActivitySearchByRegionBinding>(
    R.layout.activity_search_by_region
) {

    private val viewModel by viewModels<SearchByRegionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@SearchByRegionActivity.viewModel
            rvGu.run {
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_region_gu,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@SearchByRegionActivity.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<RegionVo>() {
                        override fun areItemsTheSame(
                            oldItem: RegionVo,
                            newItem: RegionVo
                        ): Boolean = oldItem.hashCode() == newItem.hashCode()

                        override fun areContentsTheSame(
                            oldItem: RegionVo,
                            newItem: RegionVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                ).apply {
                    itemClickListener = { item, index ->
                        if (item is RegionVo) {
                            this@SearchByRegionActivity.viewModel.selectGu(index)
                        }
                    }
                }
            }
            rvDong.run {
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_region_dong,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@SearchByRegionActivity.viewModel),
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
                            val selectedGu = this@SearchByRegionActivity.viewModel.getSelectedGu()
                            setResult(
                                RESULT_OK,
                                Intent().apply {
                                    putExtra(
                                        REGION,
                                        "$selectedGu $item"
                                    )
                                }
                            )
                            finish()
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
            clSearchByRegionMap.setOnClickListener {
                startActivity<SearchByMapActivity>()
            }
        }
    }

    companion object {
        const val REGION = "region"
    }
}
