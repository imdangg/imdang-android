package info.imdang.imdang.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityLoginBinding
import info.imdang.imdang.ui.login.bottomsheet.OnboardingBottomSheet
import info.imdang.imdang.ui.login.bottomsheet.OnboardingBottomSheetListener
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setupBinding()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@LoginActivity.viewModel
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    LoginEvent.ShowOnboardingBottomSheet -> {
                        OnboardingBottomSheet.instance(
                            listener = object : OnboardingBottomSheetListener {
                                override fun onClickLastNextButton() {
                                    // todo : 회원가입 화면으로 이동
                                }
                            }
                        ).show(
                            supportFragmentManager,
                            OnboardingBottomSheet::class.java.simpleName
                        )
                    }
                }
            }
        }
    }
}
