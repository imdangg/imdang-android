package info.imdang.imdang.ui.main.home.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import info.imdang.imdang.model.insight.InsightVo

class RecommendInsightPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val insights: List<List<InsightVo>>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return RecommendInsightsFragment.instance(insights = insights[position])
    }
}
