package info.imdang.imdang.ui.main.storage.address

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.DividerItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.databinding.ActivityInsightAddressBinding
import info.imdang.imdang.model.myinsight.MyInsightAddressVo

@AndroidEntryPoint
class InsightAddressActivity :
    BaseActivity<ActivityInsightAddressBinding>(R.layout.activity_insight_address) {

    private val viewModel by viewModels<InsightAddressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@InsightAddressActivity.viewModel
            rvInsightAddress.run {
                addItemDecoration(DividerItemDecoration())
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_insight_address,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@InsightAddressActivity.viewModel),
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
                ).apply {
                    itemClickListener = { item, _ ->
                        if (item is MyInsightAddressVo) {
                            this@InsightAddressActivity.viewModel.onClickAddress(item)
                        }
                    }
                }
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener { finish() }
            tvSelectCompleteButton.setOnClickListener {
                setResult(
                    RESULT_OK,
                    Intent().apply {
                        putExtra(
                            SELECTED_PAGE,
                            this@InsightAddressActivity.viewModel.selectedPage.value
                        )
                    }
                )
                finish()
            }
        }
    }

    companion object {
        const val SELECTED_PAGE = "selectedPage"
    }
}
