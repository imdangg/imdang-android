package info.imdang.imdang.ui.write.fragment

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.databinding.FragmentWriteInsightComplexEnvironmentBinding

@AndroidEntryPoint
class WriteInsightComplexEnvironment :
    BaseFragment<FragmentWriteInsightComplexEnvironmentBinding>(
        R.layout.fragment_write_insight_complex_environment
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun instance(): WriteInsightComplexEnvironment = WriteInsightComplexEnvironment()
    }
}
