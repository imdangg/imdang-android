package info.imdang.imdang.ui.main.home.search.recommend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import info.imdang.imdang.model.insight.InsightVo

class RecommendInsightsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val insights: List<List<InsightVo>>,
    private val listener: RecommendInsightsListener
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = insights.size

    override fun createFragment(position: Int): Fragment {
        return RecommendInsightsFragment.instance(
            insights = insights[position],
            listener = listener
        )
    }
}
