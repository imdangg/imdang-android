package info.imdang.imdang.ui.main.storage.bottomsheet

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseBottomSheetDialogFragment
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.dpToPx
import info.imdang.imdang.databinding.BottomSheetAptBinding
import info.imdang.imdang.model.insight.InsightAptVo
import info.imdang.imdang.ui.main.storage.StorageViewModel

@AndroidEntryPoint
class AptBottomSheet :
    BaseBottomSheetDialogFragment<BottomSheetAptBinding>(R.layout.bottom_sheet_apt) {

    private val viewModel by viewModels<StorageViewModel>()

    private var dialogView: View? = null

    override fun getTheme(): Int = info.imdang.component.R.style.Rounded12BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogView = view

        setupBottomSheet(view)
        setupBinding()
    }

    override fun onDestroyView() {
        dialogView?.viewTreeObserver?.addOnGlobalLayoutListener(null)
        super.onDestroyView()
    }

    private fun setupBottomSheet(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener(
            object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val bottomSheet = (dialog as BottomSheetDialog).findViewById<View>(
                        com.google.android.material.R.id.design_bottom_sheet
                    ) as FrameLayout?
                    bottomSheet?.let {
                        BottomSheetBehavior.from(it).run {
                            peekHeight = requireContext().dpToPx(436)
                            maxHeight = requireContext().dpToPx(736)
                        }
                        val newHeight = activity?.window?.decorView?.measuredHeight
                        val viewGroupLayoutParams = it.layoutParams
                        viewGroupLayoutParams.height = newHeight ?: 0
                        it.layoutParams = viewGroupLayoutParams
                    }
                }
            }
        )
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@AptBottomSheet.viewModel
            rvApt.run {
                addItemDecoration(SpaceItemDecoration(space = 8))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_apt,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@AptBottomSheet.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<InsightAptVo>() {
                        override fun areItemsTheSame(
                            oldItem: InsightAptVo,
                            newItem: InsightAptVo
                        ): Boolean = oldItem.aptName == newItem.aptName

                        override fun areContentsTheSame(
                            oldItem: InsightAptVo,
                            newItem: InsightAptVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                )
            }
        }
    }

    companion object {
        fun instance() = AptBottomSheet()
    }
}
