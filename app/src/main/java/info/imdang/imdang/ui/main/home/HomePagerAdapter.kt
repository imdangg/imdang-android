package info.imdang.imdang.ui.main.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import info.imdang.imdang.ui.main.home.exchange.HomeExchangeFragment
import info.imdang.imdang.ui.main.home.search.HomeSearchFragment

class HomePagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeSearchFragment.instance()
            else -> HomeExchangeFragment.instance()
        }
    }
}
