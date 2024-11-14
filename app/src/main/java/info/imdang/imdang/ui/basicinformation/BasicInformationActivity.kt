package info.imdang.imdang.ui.basicinformation

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityBasicInformationBinding
import info.imdang.imdang.ui.joincomplete.JoinCompleteActivity

@AndroidEntryPoint
class BasicInformationActivity : BaseActivity<ActivityBasicInformationBinding>(R.layout.activity_basic_information) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        with(binding) {
            btnComplete.setOnClickListener {
                startActivity<JoinCompleteActivity>()
            }
        }
    }
}
