package info.imdang.imdang.ui.write

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import info.imdang.imdang.ui.write.fragment.WriteInsightBasicInfoFragment
import info.imdang.imdang.ui.write.fragment.WriteInsightComplexEnvironmentFragment
import info.imdang.imdang.ui.write.fragment.WriteInsightComplexFacilityFragment
import info.imdang.imdang.ui.write.fragment.WriteInsightGoodNewsFragment
import info.imdang.imdang.ui.write.fragment.WriteInsightInfraFragment

class WriteInsightPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WriteInsightBasicInfoFragment.instance()
            1 -> WriteInsightInfraFragment.instance()
            2 -> WriteInsightComplexEnvironmentFragment.instance()
            3 -> WriteInsightComplexFacilityFragment.instance()
            4 -> WriteInsightGoodNewsFragment.instance()
            else -> WriteInsightBasicInfoFragment.instance()
        }
    }
}
