package info.imdang.imdang.ui.login.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityOnboardingBinding
import info.imdang.imdang.ui.join.BasicInformationActivity

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    private val joinResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        setResult(result.resultCode)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = getColor(info.imdang.component.R.color.gray_50)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            vpOnboarding.adapter = OnboardingPagerAdapter(
                fragmentActivity = this@OnboardingActivity
            )
            TabLayoutMediator(tlIndicator, vpOnboarding) { _, _ -> }.attach()
            tvNextButton.setOnClickListener {
                if (vpOnboarding.currentItem < 2) {
                    vpOnboarding.currentItem += 1
                } else {
                    joinResult.launch(
                        Intent(
                            this@OnboardingActivity,
                            BasicInformationActivity::class.java
                        )
                    )
                }
            }
        }
    }
}
