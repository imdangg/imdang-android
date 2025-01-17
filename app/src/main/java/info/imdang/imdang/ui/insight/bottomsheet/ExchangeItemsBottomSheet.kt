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
import info.imdang.imdang.common.bindingadapter.BaseMultiViewAdapter
import info.imdang.imdang.common.bindingadapter.ViewHolderType
import info.imdang.imdang.databinding.BottomSheetExchangeItemsBinding
import info.imdang.imdang.model.insight.ExchangeItem
import info.imdang.imdang.ui.insight.InsightDetailViewModel
import java.io.Serializable

@AndroidEntryPoint
class ExchangeItemsBottomSheet : BaseBottomSheetDialogFragment<BottomSheetExchangeItemsBinding>(
    R.layout.bottom_sheet_exchange_items
) {

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
            viewModel = this@ExchangeItemsBottomSheet.viewModel
            rvExchangeItem.run {
                addItemDecoration(DividerItemDecoration())
                adapter = BaseMultiViewAdapter(
                    viewHolderMapper = {
                        when (it) {
                            is ExchangeItem.Pass -> ExchangeItemHolderType.PassHolder
                            is ExchangeItem.Insight -> ExchangeItemHolderType.InsightHolder
                        }
                    },
                    viewHolderType = ExchangeItemHolderType::class,
                    viewModel = mapOf(BR.viewModel to this@ExchangeItemsBottomSheet.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<ExchangeItem>() {
                        override fun areItemsTheSame(
                            oldItem: ExchangeItem,
                            newItem: ExchangeItem
                        ): Boolean = oldItem == newItem

                        override fun areContentsTheSame(
                            oldItem: ExchangeItem,
                            newItem: ExchangeItem
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                ).apply {
                    itemClickListener = { item, _ ->
                        if (item is ExchangeItem) {
                            this@ExchangeItemsBottomSheet.viewModel.onClickExchangeItem(item)
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
        ) = ExchangeItemsBottomSheet().apply {
            this.listener = listener
        }
    }
}

enum class ExchangeItemHolderType(
    override val layoutResourceId: Int,
    override val bindingItemId: Int
) : ViewHolderType {
    PassHolder(
        layoutResourceId = R.layout.item_insight_detail_pass,
        bindingItemId = BR.item
    ),
    InsightHolder(
        layoutResourceId = R.layout.item_insight_detail_my_insight,
        bindingItemId = BR.item
    )
}

interface MyInsightsBottomSheetListener : Serializable {

    fun onClickConfirmButton()
}
