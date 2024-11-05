package info.imdang.imdang.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResourceId: Int
) : AppCompatActivity() {

    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<VDB>(this, layoutResourceId).apply {
            lifecycleOwner = this@BaseActivity
        }
    }
}
