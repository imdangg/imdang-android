package info.imdang.imdang.ui.write

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.hideKeyboard
import info.imdang.imdang.common.ext.setMargin
import info.imdang.imdang.databinding.ActivityWriteInsightBinding
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.write.fragment.WriteInsightBasicInfoFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WriteInsightActivity :
    BaseActivity<ActivityWriteInsightBinding>(R.layout.activity_write_insight) {

    private val viewModel by viewModels<WriteInsightViewModel>()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showCommonDialog(
            message = getString(info.imdang.component.R.string.write_insight_message),
            positiveButtonText = getString(info.imdang.component.R.string.confirm)
        )

        setupBinding()
        setupListener()
        observe()
    }

    override fun onShowKeyboard(keyboardHeight: Int) {
        super.onShowKeyboard(keyboardHeight)

        with(binding.tvWriteCompleteButton) {
            setMargin(left = 0, right = 0, top = 0, bottom = 0)
            text = getString(info.imdang.component.R.string.confirm)

            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(
                    if (isEnabled) {
                        getColor(info.imdang.component.R.color.orange_500)
                    } else {
                        getColor(
                            info.imdang.component.R.color.gray_100
                        )
                    }
                )
                cornerRadius = 0f
            }
            background = drawable
        }
    }

    override fun onHideKeyboard() {
        super.onHideKeyboard()

        with(binding.tvWriteCompleteButton) {
            setMargin(left = 20, right = 20, top = 0, bottom = 40)
            text = getString(info.imdang.component.R.string.write_complete)

            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(
                    if (isEnabled) {
                        getColor(info.imdang.component.R.color.orange_500)
                    } else {
                        getColor(
                            info.imdang.component.R.color.gray_100
                        )
                    }
                )
                cornerRadius =
                    resources.getDimension(info.imdang.component.R.dimen.default_corner_radius)
            }
            background = drawable
        }

        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment is WriteInsightBasicInfoFragment) {
                fragment.clearEditTextFocus()
            }
        }
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@WriteInsightActivity.viewModel
            vpWriteInsight.adapter = WriteInsightPagerAdapter(
                fragmentActivity = this@WriteInsightActivity
            )
            TabLayoutMediator(tlWriteInsight, vpWriteInsight) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(info.imdang.component.R.string.basic_info)
                    1 -> getString(info.imdang.component.R.string.infra)
                    2 -> getString(info.imdang.component.R.string.apt_environment)
                    3 -> getString(info.imdang.component.R.string.apt_facility)
                    else -> getString(info.imdang.component.R.string.good_news)
                }
            }.attach()
        }
    }

    private fun setupListener() {
        with(binding) {
            onBackPressedDispatcher.addCallback {
                showCommonDialog(
                    message = getString(info.imdang.component.R.string.write_insight_back_message),
                    positiveButtonText = getString(info.imdang.component.R.string.yes_its_ok),
                    negativeButtonText = getString(info.imdang.component.R.string.cancel),
                    onClickPositiveButton = {
                        finish()
                    }
                )
            }
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            tvWriteCompleteButton.setOnClickListener {
                if (isVisibleKeyboard) {
                    hideKeyboard()
                } else {
                    // todo : 작성 완료
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun observe() {
        lifecycleScope.launch {
            launch {
                viewModel.isInsightTitleFocused
                    .flatMapLatest { isFocused ->
                        if (isFocused) {
                            viewModel.isInsightTitleValid
                        } else {
                            viewModel.isFinalButtonEnabled
                        }
                    }
                    .collect { isInsightTitleValid ->
                        updateButtonState(isInsightTitleValid)
                    }
            }

            launch {
                viewModel.isInsightDateFocused
                    .flatMapLatest { isFocused ->
                        if (isFocused) {
                            viewModel.isInsightDateValid
                        } else {
                            viewModel.isFinalButtonEnabled
                        }
                    }
                    .collect { isInsightDateValid ->
                        updateButtonState(isInsightDateValid)
                    }
            }

            launch {
                viewModel.isFinalButtonEnabled.collect { isEnabled ->
                    updateButtonState(isEnabled)
                }
            }
        }
    }

    // 버튼의 배경 상태 확인
    private fun updateButtonState(isEnabled: Boolean) {
        with(binding.tvWriteCompleteButton) {
            this.isEnabled = isEnabled
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(
                    if (isEnabled) {
                        getColor(info.imdang.component.R.color.orange_500)
                    } else {
                        getColor(
                            info.imdang.component.R.color.gray_100
                        )
                    }
                )
                setTextColor(
                    if (isEnabled) {
                        getColor(info.imdang.component.R.color.white)
                    } else {
                        getColor(info.imdang.component.R.color.gray_500)
                    }
                )
                cornerRadius =
                    if (isVisibleKeyboard) {
                        0f
                    } else {
                        resources.getDimension(info.imdang.component.R.dimen.default_corner_radius)
                    }
            }
            background = drawable
        }
    }
}
