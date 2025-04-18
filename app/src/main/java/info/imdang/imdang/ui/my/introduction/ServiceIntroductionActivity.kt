package info.imdang.imdang.ui.my.introduction

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityServiceIntroductionBinding
import info.imdang.imdang.ui.main.home.search.HomeSearchFragment.Companion.FOCUS_VIEW

@AndroidEntryPoint
class ServiceIntroductionActivity :
    BaseActivity<ActivityServiceIntroductionBinding>(R.layout.activity_service_introduction) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("서비스 소개")
        setupBinding()
        scrollToFocusedView()
    }

    private fun setupBinding() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun scrollToFocusedView() {
        with(binding) {
            val focusView = intent.getBooleanExtra(FOCUS_VIEW, false)

            if (focusView) {
                nsvServiceIntroduction.post {
                    nsvServiceIntroduction.smoothScrollTo(0, ivServiceIntroduction2.top)

                    ivServiceIntroduction2.requestFocus()
                }
            }
        }
    }
}
