package info.imdang.imdang.ui.main.home.search

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.databinding.FragmentHomeSearchBinding

@AndroidEntryPoint
class HomeSearchFragment : BaseFragment<FragmentHomeSearchBinding>(R.layout.fragment_home_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun instance(): HomeSearchFragment = HomeSearchFragment()
    }
}
