package info.imdang.imdang.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    SplashEvent.CloseSplash -> {
                        // todo : 로그인 상태 o -> 메인 화면으로 이동
                        // todo : 로그인 상태 x -> 로그인 화면으로 이동
                        // startActivity<LoginActivity>()
                        Log.d("##", "CloseSplash")
                    }
                }
            }
        }
    }
}
