package info.imdang.imdang.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.FragmentHomeBinding
import info.imdang.imdang.ui.login.LoginActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@HomeFragment.viewModel
            vpHome.adapter = HomePagerAdapter(
                fragmentActivity = requireActivity()
            )
            TabLayoutMediator(tlHome, vpHome) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(info.imdang.component.R.string.home_search)
                    else -> getString(info.imdang.component.R.string.home_exchange)
                }
            }.attach()
        }
    }

    private fun setupListener() {
        with(binding) {
            ivHomeProfile.setOnClickListener {
                this@HomeFragment.viewModel.logout()
            }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    HomeEvent.Logout -> requireContext().startActivity<LoginActivity>()
                }
            }
        }
    }
}
