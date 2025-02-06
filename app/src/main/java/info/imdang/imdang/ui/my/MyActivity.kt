package info.imdang.imdang.ui.my

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.BuildConfig
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityMyBinding
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.login.LoginActivity
import info.imdang.imdang.ui.my.term.ServiceTermActivity
import info.imdang.imdang.ui.my.withdraw.WithdrawActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyActivity : BaseActivity<ActivityMyBinding>(R.layout.activity_my) {

    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupCollect()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@MyActivity.viewModel
            tvMyCurrentVersionInfo.text = getString(
                info.imdang.component.R.string.current_version_info,
                BuildConfig.VERSION_NAME
            )
        }
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            clLogout.setOnClickListener {
                showCommonDialog(
                    iconDrawableResource = info.imdang.component.R.drawable.ic_sign_for_dialog,
                    message = getString(info.imdang.component.R.string.logout_info_message),
                    negativeButtonText = getString(info.imdang.component.R.string.cancel),
                    positiveButtonText = getString(info.imdang.component.R.string.logout),
                    onClickPositiveButton = {
                        this@MyActivity.viewModel.logout()
                    }
                )
            }
            clMyServiceTerm.setOnClickListener {
                startActivity<ServiceTermActivity>()
            }
            tvWithdraw.setOnClickListener {
                startActivity<WithdrawActivity>()
            }
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    MyEvent.Logout -> {
                        startActivity<LoginActivity>(
                            bundle = bundleOf(LOGOUT to true),
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val LOGOUT = "logout"
    }
}
