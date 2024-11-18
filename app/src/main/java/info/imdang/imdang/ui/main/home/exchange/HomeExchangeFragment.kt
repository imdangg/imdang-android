package info.imdang.imdang.ui.main.home.exchange

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.databinding.FragmentHomeExchangeBinding

@AndroidEntryPoint
class HomeExchangeFragment :
    BaseFragment<FragmentHomeExchangeBinding>(R.layout.fragment_home_exchange) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun instance(): HomeExchangeFragment = HomeExchangeFragment()
    }
}
