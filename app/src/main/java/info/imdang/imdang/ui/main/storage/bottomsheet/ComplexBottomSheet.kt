package info.imdang.imdang.ui.main.storage.bottomsheet

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseBottomSheetDialogFragment
import info.imdang.imdang.common.SpaceItemDecoration
import info.imdang.imdang.common.bindingadapter.BaseSingleViewAdapter
import info.imdang.imdang.common.ext.dpToPx
import info.imdang.imdang.common.ext.screenHeight
import info.imdang.imdang.common.util.logEvent
import info.imdang.imdang.databinding.BottomSheetComplexBinding
import info.imdang.imdang.model.myinsight.AptComplexVo
import info.imdang.imdang.ui.main.storage.StorageViewModel

@AndroidEntryPoint
class ComplexBottomSheet :
    BaseBottomSheetDialogFragment<BottomSheetComplexBinding>(R.layout.bottom_sheet_complex) {

    private lateinit var viewModel: StorageViewModel

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
                            maxHeight =
                                requireActivity().screenHeight() - requireContext().dpToPx(83)
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
            viewModel = this@ComplexBottomSheet.viewModel
            rvComplex.run {
                addItemDecoration(SpaceItemDecoration(space = 8))
                adapter = BaseSingleViewAdapter(
                    layoutResourceId = R.layout.item_complex,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.viewModel to this@ComplexBottomSheet.viewModel),
                    diffUtil = object : DiffUtil.ItemCallback<AptComplexVo>() {
                        override fun areItemsTheSame(
                            oldItem: AptComplexVo,
                            newItem: AptComplexVo
                        ): Boolean = oldItem.aptComplexName == newItem.aptComplexName

                        override fun areContentsTheSame(
                            oldItem: AptComplexVo,
                            newItem: AptComplexVo
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }
                ).apply {
                    itemClickListener = { item, _ ->
                        if (item is AptComplexVo) {
                            logEvent(
                                event = "인사이트 보관 리스트(단지)",
                                category = "보관함",
                                action = "보관함_단지_click",
                                label = item.aptComplexName
                            )
                            this@ComplexBottomSheet.viewModel.updateSelectedComplex(item)
                            dismiss()
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun instance(
            viewModel: StorageViewModel
        ) = ComplexBottomSheet().apply {
            this.viewModel = viewModel
        }
    }
}
