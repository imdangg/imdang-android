package info.imdang.imdang.ui.insight

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.DividerItemDecoration
import info.imdang.imdang.common.bindingadapter.ViewHolderType
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.common.util.shareKakao
import info.imdang.imdang.databinding.ActivityInsightDetailBinding
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.insight.bottomsheet.ExchangeItemsBottomSheet
import info.imdang.imdang.ui.insight.bottomsheet.MyInsightsBottomSheetListener
import info.imdang.imdang.ui.main.MainActivity
import info.imdang.imdang.ui.main.MainActivity.Companion.MOVE_EXCHANGE
import info.imdang.imdang.ui.main.MainActivity.Companion.MOVE_STORAGE
import info.imdang.imdang.ui.write.WriteInsightActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InsightDetailActivity :
    BaseActivity<ActivityInsightDetailBinding>(R.layout.activity_insight_detail) {

    private val viewModel by viewModels<InsightDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("인사이트 상세")
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
            ivReport.setOnClickListener {
                if (this@InsightDetailActivity.viewModel.insight.value.isReported) {
                    showCommonDialog(
                        iconDrawableResource = info.imdang.component.R.drawable.ic_sign_for_dialog,
                        message = getString(info.imdang.component.R.string.already_report_message),
                        subMessage = getString(
                            info.imdang.component.R.string.already_report_sub_message
                        ),
                        positiveButtonText = getString(info.imdang.component.R.string.confirm)
                    )
                } else {
                    showCommonDialog(
                        iconDrawableResource = info.imdang.component.R.drawable.ic_sign_for_dialog,
                        message = getString(info.imdang.component.R.string.report_message),
                        subMessage = getString(info.imdang.component.R.string.report_sub_message),
                        positiveButtonText = getString(info.imdang.component.R.string.yes_its_ok),
                        negativeButtonText = getString(info.imdang.component.R.string.cancel),
                        onClickPositiveButton = {
                            this@InsightDetailActivity.viewModel.reportInsight()
                        }
                    )
                }
            }
            ivShare.setOnClickListener {
                shareKakao(
                    context = this@InsightDetailActivity,
                    insight = this@InsightDetailActivity.viewModel.insight.value
                )
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
            btnInsightDetailEdit.setOnClickListener {
                startActivity<WriteInsightActivity>(
                    bundle = bundleOf(
                        INSIGHT_ID to this@InsightDetailActivity.viewModel.insightId
                    )
                )
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
                        ExchangeItemsBottomSheet.instance(
                            listener = object : MyInsightsBottomSheetListener {
                                override fun onClickConfirmButton() {
                                    viewModel.requestExchange()
                                }
                            }
                        ).show(
                            supportFragmentManager,
                            ExchangeItemsBottomSheet::class.java.simpleName
                        )
                    }

                    is InsightDetailEvent.ShowCommonDialog -> {
                        showCommonDialog(
                            message = it.dialogType.message,
                            positiveButtonText = getString(info.imdang.component.R.string.confirm),
                            subButtonText = it.dialogType.subButtonText,
                            onClickSubButton = it.onClickSubButton
                        )
                    }

                    InsightDetailEvent.MoveHomeExchange -> {
                        startActivity<MainActivity>(
                            bundle = bundleOf(MOVE_EXCHANGE to true),
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    }

                    InsightDetailEvent.MoveStorage -> {
                        startActivity<MainActivity>(
                            bundle = bundleOf(MOVE_STORAGE to true),
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val INSIGHT_ID = "insightId"
    }
}

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
