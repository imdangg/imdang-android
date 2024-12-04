package info.imdang.imdang.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import info.imdang.imdang.common.util.KeyboardVisibilityUtils

abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResourceId: Int
) : AppCompatActivity() {

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

    override fun onDestroy() {
        super.onDestroy()

        keyboardVisibilityUtils.detachKeyboardListeners()
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
