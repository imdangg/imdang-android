package info.imdang.imdang.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.common.util.logEvent
import info.imdang.imdang.common.util.logScreen
import info.imdang.imdang.databinding.FragmentHomeBinding
import info.imdang.imdang.ui.main.MainEvent
import info.imdang.imdang.ui.main.MainViewModel
import info.imdang.imdang.ui.main.home.bottomsheet.HomeFreePassBottomSheet
import info.imdang.imdang.ui.main.home.bottomsheet.HomeFreePassBottomSheetListener
import info.imdang.imdang.ui.main.home.notification.NotificationActivity
import info.imdang.imdang.ui.main.home.notification.NotificationActivity.Companion.RESULT_STORAGE
import info.imdang.imdang.ui.my.MyActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<HomeViewModel>()

    private val notificationResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_STORAGE) {
            mainViewModel.emitEvent(MainEvent.MoveStorage)
        }
    }

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
                logEvent(
                    event = "알림",
                    category = if (vpHome.currentItem == 0) "홈_탐색" else "홈_교환소",
                    action = if (vpHome.currentItem == 0) {
                        "탐색_알림_click"
                    } else {
                        "교환소_알림_click"
                    }
                )
                this@HomeFragment.mainViewModel.hideTooltip()
                notificationResult.launch(
                    Intent(requireContext(), NotificationActivity::class.java)
                )
            }
            ivHomeProfile.setOnClickListener {
                logEvent(
                    event = "마이페이지",
                    category = if (vpHome.currentItem == 0) "홈_탐색" else "홈_교환소",
                    action = if (vpHome.currentItem == 0) {
                        "탐색_마이페이지_click"
                    } else {
                        "교환소_마이페이지_click"
                    }
                )
                this@HomeFragment.mainViewModel.hideTooltip()
                requireContext().startActivity<MyActivity>()
            }
            tlHome.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    this@HomeFragment.mainViewModel.hideTooltip()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
            vpHome.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            logScreen(
                                screenName = "홈_탐색",
                                screenClass = this::class.java.simpleName
                            )
                            logEvent(event = "홈_탐색", category = "홈_탐색")
                        }
                        1 -> {
                            logScreen(
                                screenName = "홈_교환소",
                                screenClass = this::class.java.simpleName
                            )
                            logEvent(event = "홈_교환소", category = "홈_교환소")
                        }
                    }
                }
            })
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    HomeEvent.ShowHomeFreePassBottomSheet -> showFreePassBottomSheet()
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

    fun moveExchange() {
        binding.vpHome.currentItem = 1
    }

    fun currentPage(): Int = binding.vpHome.currentItem
}
