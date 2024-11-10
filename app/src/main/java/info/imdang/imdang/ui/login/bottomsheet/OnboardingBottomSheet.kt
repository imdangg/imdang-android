package info.imdang.imdang.ui.login.bottomsheet

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseBottomSheetDialogFragment
import info.imdang.imdang.databinding.BottomSheetOnboardingBinding
import java.io.Serializable

@AndroidEntryPoint
class OnboardingBottomSheet :
    BaseBottomSheetDialogFragment<BottomSheetOnboardingBinding>(R.layout.bottom_sheet_onboarding) {

    private lateinit var listener: OnboardingBottomSheetListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            vpOnboarding.adapter = OnboardingPagerAdapter(
                fragmentActivity = this@OnboardingBottomSheet.requireActivity()
            )
            TabLayoutMediator(tlIndicator, vpOnboarding) { _, _ -> }.attach()
            tvNextButton.setOnClickListener {
                if (vpOnboarding.currentItem < 2) {
                    vpOnboarding.currentItem += 1
                } else {
                    listener.onClickLastNextButton()
                    dismiss()
                }
            }
        }
    }

    companion object {

        fun instance(
            listener: OnboardingBottomSheetListener
        ) = OnboardingBottomSheet().apply {
            this.listener = listener
        }
    }
}

interface OnboardingBottomSheetListener : Serializable {

    fun onClickLastNextButton()
}
