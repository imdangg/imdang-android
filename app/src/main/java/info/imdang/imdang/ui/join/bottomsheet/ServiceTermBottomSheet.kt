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
import java.io.Serializable

@AndroidEntryPoint
class ServiceTermBottomSheet : BaseBottomSheetDialogFragment<BottomSheetServiceTermBinding>(
    R.layout.bottom_sheet_service_term
) {

    private val viewModel by activityViewModels<BasicInformationViewModel>()

    private lateinit var listener: ServiceTermBottomSheetListener

    override fun getTheme(): Int = info.imdang.component.R.style.Rounded12BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupListener()
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

    private fun setupListener() {
        with(binding) {
            tvAgreeAndContinueButton.setOnClickListener {
                dismiss()
                listener.onClickAgreeContinueButton()
            }
        }
    }

    companion object {
        fun instance(
            listener: ServiceTermBottomSheetListener
        ) = ServiceTermBottomSheet().apply {
            this.listener = listener
        }
    }
}

interface ServiceTermBottomSheetListener : Serializable {

    fun onClickAgreeContinueButton()
}
