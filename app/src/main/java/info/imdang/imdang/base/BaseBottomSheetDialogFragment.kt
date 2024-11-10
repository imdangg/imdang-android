package info.imdang.imdang.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResourceId: Int
) : BottomSheetDialogFragment() {

    protected lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate<VDB>(inflater, layoutResourceId, container, false)
            .apply {
                lifecycleOwner = this@BaseBottomSheetDialogFragment
            }
        return binding.root
    }
}
