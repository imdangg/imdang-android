package info.imdang.imdang.ui.write.fragment

import android.os.Bundle
import android.view.View
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.databinding.FragmentWriteInsightGoodNewsBinding

class WriteInsightGoodNewsFragment : BaseFragment<FragmentWriteInsightGoodNewsBinding>(
    R.layout.fragment_write_insight_good_news
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun instance(): WriteInsightGoodNewsFragment = WriteInsightGoodNewsFragment()
    }
}
