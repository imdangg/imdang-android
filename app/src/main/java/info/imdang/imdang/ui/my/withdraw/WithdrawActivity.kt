package info.imdang.imdang.ui.my.withdraw

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityWithdrawBinding
import info.imdang.imdang.ui.login.LoginActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WithdrawActivity : BaseActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {

    private val viewModel by viewModels<WithdrawViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("계정 탈퇴")
        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@WithdrawActivity.viewModel
        }
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    WithdrawEvent.Withdraw -> {
                        startActivity<LoginActivity>(
                            bundle = bundleOf(WITHDRAW to true),
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val WITHDRAW = "withdraw"
    }
}
