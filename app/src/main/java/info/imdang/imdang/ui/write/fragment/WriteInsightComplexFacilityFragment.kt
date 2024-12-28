package info.imdang.imdang.ui.write.fragment

import android.os.Bundle
import android.view.View
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.databinding.FragmentWriteInsightComplexFacilityBinding

class WriteInsightComplexFacilityFragment :
    BaseFragment<FragmentWriteInsightComplexFacilityBinding>(
        R.layout.fragment_write_insight_complex_facility
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun instance(): WriteInsightComplexFacilityFragment = WriteInsightComplexFacilityFragment()
    }
}
