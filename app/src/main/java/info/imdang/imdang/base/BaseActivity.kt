package info.imdang.imdang.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import info.imdang.imdang.common.util.KeyboardVisibilityUtils
import info.imdang.imdang.common.util.logScreen

abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResourceId: Int
) : AppCompatActivity() {

    private lateinit var screenName: String

    protected lateinit var binding: VDB

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    protected var isVisibleKeyboard: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<VDB>(this, layoutResourceId).apply {
            lifecycleOwner = this@BaseActivity
        }

        setupKeyboardListener()
    }

    override fun onResume() {
        super.onResume()
        if (this::screenName.isInitialized) {
            logScreen(screenName = screenName, screenClass = this::class.java.simpleName)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        keyboardVisibilityUtils.detachKeyboardListeners()
    }

    /**
     * setName을 호출하지 않으면 screen_view 이벤트가 수집되지 않음
     * @param screenName GA에 기록 될 화면 이름
     */
    fun setName(screenName: String) {
        this.screenName = screenName
    }

    open fun onShowKeyboard(keyboardHeight: Int) {}

    open fun onHideKeyboard() {}

    private fun setupKeyboardListener() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(
            window,
            onShowKeyboard = {
                isVisibleKeyboard = true
                onShowKeyboard(it)
            },
            onHideKeyboard = {
                isVisibleKeyboard = false
                onHideKeyboard()
            }
        )
    }
}
