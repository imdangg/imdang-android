package info.imdang.imdang.ui.insight

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.base.BaseDialog
import info.imdang.imdang.common.DividerItemDecoration
import info.imdang.imdang.common.bindingadapter.ViewHolderType
import info.imdang.imdang.common.bindingadapter.bindVisible
import info.imdang.imdang.databinding.ActivityInsightDetailBinding
import info.imdang.imdang.databinding.DialogExchangeBinding
import info.imdang.imdang.ui.insight.bottomsheet.MyInsightsBottomSheet
import info.imdang.imdang.ui.insight.bottomsheet.MyInsightsBottomSheetListener
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InsightDetailActivity :
    BaseActivity<ActivityInsightDetailBinding>(R.layout.activity_insight_detail) {

    private val viewModel by viewModels<InsightDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@InsightDetailActivity.viewModel
            rvInsightDetail.run {
                addItemDecoration(
                    DividerItemDecoration(
                        height = 8,
                        color = getColor(info.imdang.component.R.color.gray_50)
                    )
                )
                adapter = InsightDetailAdapter(viewModel = this@InsightDetailActivity.viewModel)
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            tlInsightDetail.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    scrollToPosition(tab?.position ?: -1)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    scrollToPosition(tab?.position ?: -1)
                }
            })
            rvInsightDetail.setOnScrollChangeListener { _, _, _, _, _ ->
                if (!this@InsightDetailActivity.viewModel.isScrolling.value) {
                    val layoutManager = binding.rvInsightDetail.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    binding.tlInsightDetail.setScrollPosition(
                        firstVisibleItemPosition,
                        0f,
                        true
                    )
                }
            }
        }
    }

    private fun scrollToPosition(position: Int) {
        viewModel.onClickTab()
        val layoutManager = binding.rvInsightDetail.layoutManager as LinearLayoutManager
        if (this@InsightDetailActivity.viewModel.isEnableTabMove()) {
            layoutManager.scrollToPositionWithOffset(position, 0)
        } else {
            layoutManager.scrollToPositionWithOffset(if (position == 0) 0 else 1, 0)
        }
        if (position > 0) binding.ablInsightDetail.setExpanded(false)
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    InsightDetailEvent.ShowMyInsightsBottomSheet -> {
                        MyInsightsBottomSheet.instance(
                            listener = object : MyInsightsBottomSheetListener {
                                override fun onClickConfirmButton() {
                                    viewModel.requestExchange()
                                }
                            }
                        ).show(
                            supportFragmentManager,
                            MyInsightsBottomSheet::class.java.simpleName
                        )
                    }
                    is InsightDetailEvent.ShowExchangeDialog -> {
                        showExchangeDialog(
                            message = it.message,
                            checkButtonText = it.checkButtonText
                        )
                    }
                }
            }
        }
    }

    private fun showExchangeDialog(message: String, checkButtonText: String? = null) {
        BaseDialog<DialogExchangeBinding>(
            context = this,
            layoutResourceId = R.layout.dialog_exchange
        ).onShow { binding ->
            with(binding) {
                tvMessage.text = message
                tvExchangeCheckButton.text = checkButtonText
                tvExchangeCheckButton.bindVisible(checkButtonText != null)
                tvExchangeCheckButton.setOnClickListener {
                    if (isExchangeButton(checkButtonText)) {
                        // todo : 교환소 화면으로 이동
                    }
                    if (isStorageButton(checkButtonText)) {
                        // todo : 보관함 화면으로 이동
                    }
                }
                tvConfirmButton.setOnClickListener { dismiss() }
            }
        }.show()
    }

    private fun isExchangeButton(checkButtonText: String?) = checkButtonText?.contains(
        getString(info.imdang.component.R.string.home_exchange)
    ) == true

    private fun isStorageButton(checkButtonText: String?) = checkButtonText?.contains(
        getString(info.imdang.component.R.string.storage)
    ) == true

    enum class InsightDetailHolderType(
        override val layoutResourceId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        BasicInfoHolder(
            layoutResourceId = R.layout.item_insight_detail_basic_info,
            bindingItemId = BR.item
        ),
        InfraHolder(
            layoutResourceId = R.layout.item_insight_detail_infra,
            bindingItemId = BR.item
        ),
        AptEnvironmentHolder(
            layoutResourceId = R.layout.item_insight_detail_apt_environment,
            bindingItemId = BR.item
        ),
        AptFacilityHolder(
            layoutResourceId = R.layout.item_insight_detail_apt_facility,
            bindingItemId = BR.item
        ),
        GoodNewsHolder(
            layoutResourceId = R.layout.item_insight_detail_good_news,
            bindingItemId = BR.item
        ),
        InvisibleHolder(
            layoutResourceId = R.layout.item_insight_detail_invisible,
            bindingItemId = BR.item
        )
    }
}
