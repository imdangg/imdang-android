package info.imdang.imdang.ui.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.BuildConfig
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.startAndFinishActivity
import info.imdang.imdang.databinding.ActivityLoginBinding
import info.imdang.imdang.ui.join.BasicInformationActivity
import info.imdang.imdang.ui.login.bottomsheet.OnboardingBottomSheet
import info.imdang.imdang.ui.login.bottomsheet.OnboardingBottomSheetListener
import info.imdang.imdang.ui.main.MainActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var userApiClient: UserApiClient
    private lateinit var signInClient: SignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private val googleLoginResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val credential = signInClient.getSignInCredentialFromIntent(result.data)
            val firebaseCredential = GoogleAuthProvider.getCredential(
                credential.googleIdToken,
                null
            )
            firebaseAuth.signInWithCredential(firebaseCredential)
                .addOnSuccessListener {
                    lifecycleScope.launch {
                        handleSocialLoginResult(it.user?.getIdToken(true)?.await()?.token)
                    }
                }
                .addOnFailureListener {
                    handleSocialLoginResult(error = it)
                }
        }
    }

    private val joinResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) startAndFinishActivity<MainActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        userApiClient = UserApiClient.instance
        signInClient = Identity.getSignInClient(this)
        firebaseAuth = Firebase.auth
        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@LoginActivity.viewModel
            clKakaoLogin.setOnClickListener { kakaoLogin() }
            clGoogleLogin.setOnClickListener { googleLogin() }
        }
    }

    private fun kakaoLogin() {
        if (userApiClient.isKakaoTalkLoginAvailable(this)) {
            userApiClient.loginWithKakaoTalk(this) { oAuthToken, error ->
                handleSocialLoginResult(oAuthToken?.accessToken, error)
            }
        } else {
            userApiClient.loginWithKakaoAccount(this) { oAuthToken, error ->
                handleSocialLoginResult(oAuthToken?.accessToken, error)
            }
        }
    }

    private fun googleLogin() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
        lifecycleScope.launch {
            try {
                val signInResult = signInClient.beginSignIn(signInRequest).await()
                googleLoginResult.launch(
                    IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
                )
            } catch (e: IntentSender.SendIntentException) {
                handleSocialLoginResult(error = e)
            }
        }
    }

    private fun handleSocialLoginResult(
        token: String? = null,
        error: Throwable? = null
    ) {
        if (token != null) {
            showOnboardingBottomSheet(token)
        } else if (error != null) {
            // todo : 로그인 실패 처리
            Log.e("##", Log.getStackTraceString(error))
        }
    }

    private fun showOnboardingBottomSheet(token: String) {
        OnboardingBottomSheet.instance(
            listener = object : OnboardingBottomSheetListener {
                override fun onClickLastNextButton() {
                    // todo : token을 가지고 회원가입 화면으로 이동
                    joinResult.launch(
                        Intent(
                            this@LoginActivity,
                            BasicInformationActivity::class.java
                        )
                    )
                }
            }
        ).show(
            supportFragmentManager,
            OnboardingBottomSheet::class.java.simpleName
        )
    }
}
