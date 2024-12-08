package info.imdang.imdang.ui.write

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.hideKeyboard
import info.imdang.imdang.common.ext.setMargin
import info.imdang.imdang.databinding.ActivityWriteInsightBinding

@AndroidEntryPoint
class WriteInsightActivity :
    BaseActivity<ActivityWriteInsightBinding>(R.layout.activity_write_insight) {

    private val viewModel by viewModels<WriteInsightViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
    }

    override fun onShowKeyboard(keyboardHeight: Int) {
        super.onShowKeyboard(keyboardHeight)

        with(binding.tvWriteCompleteButton) {
            setMargin(left = 0, right = 0, top = 0, bottom = 0)
            setBackgroundResource(info.imdang.component.R.color.orange_500)
            text = getString(info.imdang.component.R.string.confirm)
            setTextColor(getColor(info.imdang.component.R.color.white))
        }
    }

    override fun onHideKeyboard() {
        super.onHideKeyboard()

        with(binding.tvWriteCompleteButton) {
            setMargin(left = 20, right = 20, top = 0, bottom = 40)
            setBackgroundResource(info.imdang.component.R.drawable.bg_complete_button)
            text = getString(info.imdang.component.R.string.write_complete)
            setTextColor(getColor(info.imdang.component.R.color.gray_500))
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
            tvWriteCompleteButton.setOnClickListener {
                if (isVisibleKeyboard) {
                    hideKeyboard()
                } else {
                    // todo : 작성 완료
                }
            }
        }
    }
}
