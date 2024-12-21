package info.imdang.imdang.ui.write

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import info.imdang.imdang.ui.write.fragment.WriteInsightBasicInfoFragment
import info.imdang.imdang.ui.write.fragment.WriteInsightInfraFragment

class WriteInsightPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WriteInsightBasicInfoFragment.instance()
            1 -> WriteInsightInfraFragment.instance()
            else -> WriteInsightBasicInfoFragment.instance()
        }
    }
}
