package info.imdang.imdang.ui.my.introduction

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityServiceIntroductionBinding

@AndroidEntryPoint
class ServiceIntroductionActivity :
    BaseActivity<ActivityServiceIntroductionBinding>(R.layout.activity_service_introduction) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }
}
