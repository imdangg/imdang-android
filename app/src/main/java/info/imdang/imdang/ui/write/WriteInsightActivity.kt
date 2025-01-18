package info.imdang.imdang.ui.write

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
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

        with(binding.clWriteButton) {
            setMargin(left = 0, right = 0, top = 0, bottom = 0)
        }
        with(binding.tvWriteCompleteButton) {
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

        with(binding.clWriteButton) {
            setMargin(left = 20, right = 20, top = 0, bottom = 40)
        }
        with(binding.tvWriteCompleteButton) {
            text = getString(info.imdang.component.R.string.next)

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
            vpWriteInsight.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    this@WriteInsightActivity.viewModel.updateSelectedPage(position)
                    tvWriteCompleteButton.text = getString(
                        if (position < 4) {
                            info.imdang.component.R.string.next
                        } else {
                            info.imdang.component.R.string.write_complete_and_upload
                        }
                    )
                }
            })
            tvWritePreviousButton.setOnClickListener {
                vpWriteInsight.currentItem =
                    this@WriteInsightActivity.viewModel.selectedPage.value - 1
            }
            tvWriteCompleteButton.setOnClickListener {
                if (isVisibleKeyboard) {
                    hideKeyboard()
                } else {
                    with(this@WriteInsightActivity.viewModel) {
                        if (selectedPage.value < 4) {
                            vpWriteInsight.currentItem = selectedPage.value + 1
                        } else {
                            this@WriteInsightActivity.viewModel.writeInsight()
                        }
                    }
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
                            viewModel.isFinalButtonEnabled()
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
                            viewModel.isFinalButtonEnabled()
                        }
                    }
                    .collect { isInsightDateValid ->
                        updateButtonState(isInsightDateValid)
                    }
            }

            launch {
                viewModel.selectedPage.flatMapLatest {
                    viewModel.isFinalButtonEnabled()
                }.collect { isEnabled ->
                    viewModel.updateProgress()
                    updateButtonState(isEnabled)
                }
            }

            launch {
                viewModel.event.collect {
                    when (it) {
                        WriteInsightEvent.WriteInsightComplete -> {
                            showCommonDialog(
                                message = getString(
                                    info.imdang.component.R.string.write_insight_complete_message
                                ),
                                positiveButtonText = getString(
                                    info.imdang.component.R.string.confirm
                                ),
                                subButtonText = "보관함 확인하기",
                                onClickPositiveButton = {
                                    finish()
                                },
                                onClickSubButton = {
                                    setResult(RESULT_OK)
                                    finish()
                                }
                            )
                        }
                        is WriteInsightEvent.ShowToast -> {
                            Toast.makeText(
                                this@WriteInsightActivity,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
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
                        getColor(info.imdang.component.R.color.gray_100)
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
