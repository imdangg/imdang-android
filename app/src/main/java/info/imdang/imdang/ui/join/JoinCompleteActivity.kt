package info.imdang.imdang.ui.join

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityJoinCompleteBinding

@AndroidEntryPoint
class JoinCompleteActivity :
    BaseActivity<ActivityJoinCompleteBinding>(R.layout.activity_join_complete) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("가입완료")
        setupListener()
    }

    private fun setupListener() {
        with(binding) {
            btnStartComplete.setOnClickListener {
                finish()
            }
        }
    }
}
