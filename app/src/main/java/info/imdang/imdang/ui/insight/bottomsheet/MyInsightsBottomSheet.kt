package info.imdang.imdang.ui.insight.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseBottomSheetDialogFragment
import info.imdang.imdang.common.DividerItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.databinding.BottomSheetMyInsightsBinding
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.ui.insight.InsightDetailViewModel
import java.io.Serializable

@AndroidEntryPoint
class MyInsightsBottomSheet :
    BaseBottomSheetDialogFragment<BottomSheetMyInsightsBinding>(R.layout.bottom_sheet_my_insights) {

    private val viewModel by activityViewModels<InsightDetailViewModel>()

    private lateinit var listener: MyInsightsBottomSheetListener

    override fun getTheme(): Int = info.imdang.component.R.style.Rounded12BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupListener()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@MyInsightsBottomSheet.viewModel
            rvMyInsights.run {
                addItemDecoration(DividerItemDecoration())
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_insight_detail_my_insight,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@MyInsightsBottomSheet.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<InsightVo>() {
                        override fun areItemsTheSame(
                            oldItem: InsightVo,
                            newItem: InsightVo
                        ): Boolean = oldItem.insightId == newItem.insightId

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
                            this@MyInsightsBottomSheet.viewModel.onClickMyInsight(item)
                        }
                    }
                }
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            tvConfirmButton.setOnClickListener {
                dismiss()
                listener.onClickConfirmButton()
            }
        }
    }

    companion object {

        fun instance(
            listener: MyInsightsBottomSheetListener
        ) = MyInsightsBottomSheet().apply {
            this.listener = listener
        }
    }
}

interface MyInsightsBottomSheetListener : Serializable {

    fun onClickConfirmButton()
}
