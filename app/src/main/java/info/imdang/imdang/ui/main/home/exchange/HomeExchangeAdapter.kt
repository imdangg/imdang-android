package info.imdang.imdang.ui.main.home.exchange

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import info.imdang.imdang.ui.main.home.history.ExchangeType
import info.imdang.imdang.ui.main.home.history.HomeHistoryRequestedFragment

class HomeExchangeAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeHistoryRequestedFragment.instance(ExchangeType.REQUESTED)
            1 -> HomeHistoryRequestedFragment.instance(ExchangeType.RECEIVED)
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
