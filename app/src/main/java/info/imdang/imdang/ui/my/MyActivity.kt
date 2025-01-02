package info.imdang.imdang.ui.my

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityMyBinding
import info.imdang.imdang.ui.my.term.ServiceTermActivity

@AndroidEntryPoint
class MyActivity : BaseActivity<ActivityMyBinding>(R.layout.activity_my) {

    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@MyActivity.viewModel
        }
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            clMyServiceTerm.setOnClickListener {
                startActivity<ServiceTermActivity>()
            }
        }
    }
}
