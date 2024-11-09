package info.imdang.imdang.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@LoginActivity.viewModel
        }
    }
}
