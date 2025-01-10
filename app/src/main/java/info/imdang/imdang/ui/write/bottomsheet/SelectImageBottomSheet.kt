package info.imdang.imdang.ui.write.bottomsheet

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseBottomSheetDialogFragment
import info.imdang.imdang.databinding.BottomSheetSelectImageBinding
import java.io.Serializable

@AndroidEntryPoint
class SelectImageBottomSheet : BaseBottomSheetDialogFragment<BottomSheetSelectImageBinding>(
    R.layout.bottom_sheet_select_image
) {

    private lateinit var listener: SelectImageBottomSheetListener

    override fun getTheme(): Int = info.imdang.component.R.style.Rounded12BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
    }

    private fun setupListener() {
        with(binding) {
            ivSelectImageClose.setOnClickListener {
                dismiss()
            }
            tvSelectImageFromAlbumButton.setOnClickListener {
                dismiss()
                listener.onClickSelectFromAlbum()
            }
            tvSelectImageFromPictureButton.setOnClickListener {
                dismiss()
                listener.onClickTakePicture()
            }
        }
    }

    companion object {
        fun instance(
            listener: SelectImageBottomSheetListener
        ) = SelectImageBottomSheet().apply {
            this.listener = listener
        }
    }
}

interface SelectImageBottomSheetListener : Serializable {

    fun onClickSelectFromAlbum()

    fun onClickTakePicture()
}
