package info.imdang.imdang.ui.main.home.exchange

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.databinding.FragmentHomeExchangeBinding

@AndroidEntryPoint
class HomeExchangeFragment :
    BaseFragment<FragmentHomeExchangeBinding>(R.layout.fragment_home_exchange) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            vpHomeExchange.adapter = HomeExchangeAdapter(
                fragmentActivity = requireActivity()
            )
            TabLayoutMediator(tlHomeExchange, vpHomeExchange) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(info.imdang.component.R.string.requested_details)
                    else -> getString(info.imdang.component.R.string.received_details)
                }
            }.attach()
        }
    }

    companion object {
        fun instance(): HomeExchangeFragment = HomeExchangeFragment()
    }
}
