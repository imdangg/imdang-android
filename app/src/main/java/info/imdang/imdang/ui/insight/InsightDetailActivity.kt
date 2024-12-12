package info.imdang.imdang.ui.insight

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityInsightDetailBinding

@AndroidEntryPoint
class InsightDetailActivity :
    BaseActivity<ActivityInsightDetailBinding>(R.layout.activity_insight_detail) {

    private val viewModel by viewModels<InsightDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@InsightDetailActivity.viewModel
        }
    }
}
