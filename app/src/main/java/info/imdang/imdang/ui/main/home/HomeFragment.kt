package info.imdang.imdang.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
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
}
