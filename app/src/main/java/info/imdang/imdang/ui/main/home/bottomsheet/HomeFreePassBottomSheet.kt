package info.imdang.imdang.ui.main.home.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseBottomSheetDialogFragment
import info.imdang.imdang.databinding.BottomSheetHomeFreePassBinding
import info.imdang.imdang.ui.main.home.HomeViewModel
import java.io.Serializable

@AndroidEntryPoint
class HomeFreePassBottomSheet : BaseBottomSheetDialogFragment<BottomSheetHomeFreePassBinding>(
    R.layout.bottom_sheet_home_free_pass
) {

    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var listener: HomeFreePassBottomSheetListener

    override fun getTheme(): Int = info.imdang.component.R.style.Rounded12BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@HomeFreePassBottomSheet.viewModel
            ivClose.setOnClickListener { dismiss() }
            tvNotShowToday.setOnClickListener {
                listener.onClickNotShowTodayButton()
                dismiss()
            }
            tvAgreeAndReceive.setOnClickListener {
                listener.onClickAgreeAndReceive()
                dismiss()
            }
        }
    }

    companion object {

        fun instance(
            listener: HomeFreePassBottomSheetListener
        ) = HomeFreePassBottomSheet().apply {
            this.listener = listener
        }
    }
}

interface HomeFreePassBottomSheetListener : Serializable {

    fun onClickNotShowTodayButton()

    fun onClickAgreeAndReceive()
}
