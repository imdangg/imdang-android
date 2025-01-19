package info.imdang.imdang.ui.join.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseBottomSheetDialogFragment
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.BottomSheetServiceTermBinding
import info.imdang.imdang.ui.join.BasicInformationViewModel
import info.imdang.imdang.ui.login.LoginActivity

@AndroidEntryPoint
class ServiceTermBottomSheet : BaseBottomSheetDialogFragment<BottomSheetServiceTermBinding>(
    R.layout.bottom_sheet_service_term
) {

    private val viewModel by activityViewModels<BasicInformationViewModel>()

    override fun getTheme(): Int = info.imdang.component.R.style.Rounded12BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@ServiceTermBottomSheet.viewModel
            isCancelable = false
            ivClose.setOnClickListener {
                requireActivity().startActivity<LoginActivity>(
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                )
            }
        }
    }

    companion object {
        fun instance() = ServiceTermBottomSheet()
    }
}
