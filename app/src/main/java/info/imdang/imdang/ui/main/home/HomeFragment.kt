package info.imdang.imdang.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.FragmentHomeBinding
import info.imdang.imdang.ui.login.LoginActivity
import info.imdang.imdang.ui.main.MainViewModel
import info.imdang.imdang.ui.main.home.bottomsheet.HomeFreePassBottomSheet
import info.imdang.imdang.ui.main.home.bottomsheet.HomeFreePassBottomSheetListener
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            mainViewModel = this@HomeFragment.mainViewModel
            viewModel = this@HomeFragment.viewModel
            vpHome.adapter = HomePagerAdapter(fragmentActivity = requireActivity())
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
            ivHomeAlarm.setOnClickListener {
                this@HomeFragment.mainViewModel.hideTooltip()
            }
            ivHomeProfile.setOnClickListener {
                this@HomeFragment.mainViewModel.hideTooltip()
                this@HomeFragment.viewModel.logout()
            }
            tlHome.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    this@HomeFragment.mainViewModel.hideTooltip()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    HomeEvent.ShowHomeFreePassBottomSheet -> showFreePassBottomSheet()
                    HomeEvent.Logout -> requireContext().startActivity<LoginActivity>()
                }
            }
        }
    }

    private fun showFreePassBottomSheet() {
        HomeFreePassBottomSheet.instance(
            listener = object : HomeFreePassBottomSheetListener {
                override fun onClickNotShowTodayButton() {
                    viewModel.setCloseTimeOfHomeFreePass()
                }

                override fun onClickAgreeAndReceive() {
                    mainViewModel.showTooltip()
                }
            }
        ).show(
            requireActivity().supportFragmentManager,
            HomeFreePassBottomSheet::class.java.simpleName
        )
    }
}
