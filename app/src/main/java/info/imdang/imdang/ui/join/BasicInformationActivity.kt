package info.imdang.imdang.ui.join

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.hideKeyboard
import info.imdang.imdang.common.ext.setMargin
import info.imdang.imdang.common.ext.startAndFinishActivity
import info.imdang.imdang.databinding.ActivityBasicInformationBinding

@AndroidEntryPoint
class BasicInformationActivity :
    BaseActivity<ActivityBasicInformationBinding>(R.layout.activity_basic_information) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    override fun onShowKeyboard(keyboardHeight: Int) {
        super.onShowKeyboard(keyboardHeight)

        with(binding.btnComplete) {
            setMargin(left = 0, right = 0, top = 0, bottom = 0)
            setBackgroundResource(info.imdang.component.R.color.orange_500)
            text = getString(info.imdang.component.R.string.confirm)
            setTextColor(getColor(info.imdang.component.R.color.white))
        }
    }

    override fun onHideKeyboard() {
        super.onHideKeyboard()

        with(binding.btnComplete) {
            setMargin(left = 20, right = 20, top = 0, bottom = 40)
            setBackgroundResource(info.imdang.component.R.drawable.bg_complete_button)
            text = getString(info.imdang.component.R.string.complete)
            setTextColor(getColor(info.imdang.component.R.color.gray_500))
        }
    }

    private fun init() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnComplete.setOnClickListener {
                if (isVisibleKeyboard) {
                    hideKeyboard()
                } else {
                    setResult(RESULT_OK)
                    startAndFinishActivity<JoinCompleteActivity>()
                }
            }
        }
    }
}
