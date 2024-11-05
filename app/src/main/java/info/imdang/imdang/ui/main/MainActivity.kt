package info.imdang.imdang.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@MainActivity.viewModel
        }
    }
}
