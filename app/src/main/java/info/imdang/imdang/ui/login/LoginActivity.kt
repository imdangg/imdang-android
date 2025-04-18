package info.imdang.imdang.ui.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.component.showToast
import info.imdang.imdang.BuildConfig
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.startAndFinishActivity
import info.imdang.imdang.databinding.ActivityLoginBinding
import info.imdang.imdang.model.auth.LoginType
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.login.onboarding.OnboardingActivity
import info.imdang.imdang.ui.main.MainActivity
import info.imdang.imdang.ui.my.MyActivity.Companion.LOGOUT
import info.imdang.imdang.ui.my.withdraw.WithdrawActivity.Companion.WITHDRAW
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var userApiClient: UserApiClient
    private lateinit var signInClient: SignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private val googleLoginResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val googleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            googleSignInAccount.getResult(ApiException::class.java).serverAuthCode?.let {
                viewModel.getGoogleAccessToken(
                    it,
                    onSuccess = { accessToken ->
                        handleSocialLoginResult(
                            loginType = LoginType.GOOGLE,
                            token = accessToken
                        )
                    },
                    onError = {
                        handleSocialLoginResult(error = it)
                    }
                )
            }
        } else {
            showToast(message = "구글 로그인에 실패 했습니다.")
        }
    }

    private val onboardingResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) startAndFinishActivity<MainActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setName("로그인")
        userApiClient = UserApiClient.instance
        signInClient = Identity.getSignInClient(this)
        firebaseAuth = Firebase.auth
        setupBinding()
        setupCollect()
        setupExtra()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@LoginActivity.viewModel
            clKakaoLogin.setOnClickListener { kakaoLogin() }
            clGoogleLogin.setOnClickListener { googleLogin() }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    is LoginEvent.ShowToast -> showToast(message = it.message)
                    LoginEvent.MoveMainActivity -> startAndFinishActivity<MainActivity>()
                    LoginEvent.MoveOnboardingActivity -> onboardingResult.launch(
                        Intent(
                            this@LoginActivity,
                            OnboardingActivity::class.java
                        )
                    )
                }
            }
        }
    }

    private fun setupExtra() {
        val isLogout = intent.getBooleanExtra(LOGOUT, false)
        if (isLogout) {
            showCommonDialog(
                message = getString(info.imdang.component.R.string.logout_success_message),
                positiveButtonText = getString(info.imdang.component.R.string.confirm)
            )
        }

        val isWithdraw = intent.getBooleanExtra(WITHDRAW, false)
        if (isWithdraw) {
            showCommonDialog(
                message = getString(info.imdang.component.R.string.withdraw_success_message),
                positiveButtonText = getString(info.imdang.component.R.string.confirm)
            )
        }
    }

    private fun kakaoLogin() {
        if (userApiClient.isKakaoTalkLoginAvailable(this)) {
            userApiClient.loginWithKakaoTalk(this) { oAuthToken, error ->
                handleSocialLoginResult(
                    loginType = LoginType.KAKAO,
                    token = oAuthToken?.accessToken,
                    error = error
                )
            }
        } else {
            userApiClient.loginWithKakaoAccount(this) { oAuthToken, error ->
                handleSocialLoginResult(
                    loginType = LoginType.KAKAO,
                    token = oAuthToken?.accessToken,
                    error = error
                )
            }
        }
    }

    private fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        lifecycleScope.launch {
            try {
                googleLoginResult.launch(signInIntent)
            } catch (e: IntentSender.SendIntentException) {
                handleSocialLoginResult(error = e)
            }
        }
    }

    private fun handleSocialLoginResult(
        loginType: LoginType? = null,
        token: String? = null,
        error: Throwable? = null
    ) {
        if (loginType != null && token != null) {
            when (loginType) {
                LoginType.KAKAO -> viewModel.kakaoLogin(token)
                LoginType.GOOGLE -> viewModel.googleLogin(token)
            }
        } else if (error != null) {
            // todo : 로그인 실패 처리
            Log.e("##", Log.getStackTraceString(error))
        }
    }
}
