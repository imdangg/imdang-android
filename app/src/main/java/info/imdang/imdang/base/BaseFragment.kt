package info.imdang.imdang.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import info.imdang.imdang.common.util.logScreen

abstract class BaseFragment<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResourceId: Int
) : Fragment() {

    private lateinit var screenName: String

    protected lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<VDB>(
            inflater,
            layoutResourceId,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (this::screenName.isInitialized) {
            logScreen(screenName = screenName, screenClass = this::class.java.simpleName)
        }
    }

    /**
     * setName을 호출하지 않으면 screen_view 이벤트가 수집되지 않음
     * @param screenName GA에 기록 될 화면 이름
     */
    fun setName(screenName: String) {
        this.screenName = screenName
    }
}
