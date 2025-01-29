package info.imdang.imdang.ui.login.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.databinding.FragmentOnboardingBinding

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {

    private val viewModel by viewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupListener()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@OnboardingFragment.viewModel
        }
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                requireActivity().finish()
            }
        }
    }

    companion object {
        const val ONBOARDING_POSITION = "onboarding_position"

        fun instance(position: Int): OnboardingFragment = OnboardingFragment().apply {
            arguments = bundleOf(ONBOARDING_POSITION to position)
        }
    }
}
