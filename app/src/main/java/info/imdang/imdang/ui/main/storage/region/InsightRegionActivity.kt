package info.imdang.imdang.ui.main.storage.region

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.DividerItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.databinding.ActivityInsightRegionBinding
import info.imdang.imdang.model.insight.InsightRegionVo

@AndroidEntryPoint
class InsightRegionActivity :
    BaseActivity<ActivityInsightRegionBinding>(R.layout.activity_insight_region) {

    private val viewModel by viewModels<InsightRegionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@InsightRegionActivity.viewModel
            rvInsightRegion.run {
                addItemDecoration(DividerItemDecoration())
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_insight_region,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@InsightRegionActivity.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<InsightRegionVo>() {
                        override fun areItemsTheSame(
                            oldItem: InsightRegionVo,
                            newItem: InsightRegionVo
                        ): Boolean = oldItem.regionId == newItem.regionId

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

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener { finish() }
            tvSelectCompleteButton.setOnClickListener {
                // todo : 선택한 지역 이전 화면에 적용
                finish()
            }
        }
    }
}
