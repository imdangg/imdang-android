package info.imdang.imdang.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.component.showToast
import info.imdang.imdang.ActivityTracker
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityMainBinding
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.login.LoginActivity
import info.imdang.imdang.ui.main.home.HomeFragment
import info.imdang.imdang.ui.main.storage.StorageFragment
import info.imdang.imdang.ui.write.WriteInsightActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var homeFragment: HomeFragment
    private lateinit var storageFragment: StorageFragment

    private val writeInsightResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            binding.bnvMain.selectedItemId = R.id.menu_storage
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupFragment()
        setupCollect()
        setupExtra()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@MainActivity.viewModel
        }
    }

    private fun setupListener() {
        with(binding) {
            fabMain.setOnClickListener {
                this@MainActivity.viewModel.hideTooltip()
                writeInsightResult.launch(
                    Intent(this@MainActivity, WriteInsightActivity::class.java)
                )
            }
            bnvMain.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> replaceFragment(homeFragment, storageFragment)
                    R.id.menu_storage -> {
                        this@MainActivity.viewModel.hideTooltip()
                        replaceFragment(storageFragment, homeFragment)
                    }
                }
                true
            }
        }
    }

    private fun setupFragment() {
        homeFragment = HomeFragment()
        storageFragment = StorageFragment()
        supportFragmentManager.beginTransaction().run {
            add(binding.flMain.id, homeFragment)
            add(binding.flMain.id, storageFragment)
            commit()
        }
        replaceFragment(homeFragment, storageFragment)
    }

    private fun replaceFragment(showFragment: Fragment, hideFragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            show(showFragment)
            hide(hideFragment)
            commit()
        }
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    is MainEvent.ShowAlert -> {
                        ActivityTracker.currentActivity?.showCommonDialog(
                            iconDrawableResource =
                                info.imdang.component.R.drawable.ic_sign_for_dialog,
                            message = it.message,
                            subMessage = it.subMessage,
                            positiveButtonText = getString(info.imdang.component.R.string.confirm)
                        )
                    }
                    is MainEvent.ShowToast -> showToast(message = it.message)
                    MainEvent.MoveStorage -> binding.bnvMain.selectedItemId = R.id.menu_storage
                    MainEvent.Logout -> {
                        startActivity<LoginActivity>(
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    }
                }
            }
        }
    }

    private fun setupExtra() {
        val isMoveExchange = intent.getBooleanExtra(MOVE_EXCHANGE, false)
        if (isMoveExchange) {
            lifecycleScope.launch {
                delay(100)
                homeFragment.moveExchange()
            }
        }

        val isMoveStorage = intent.getBooleanExtra(MOVE_STORAGE, false)
        if (isMoveStorage) {
            lifecycleScope.launch {
                delay(100)
                binding.bnvMain.selectedItemId = R.id.menu_storage
            }
        }
    }

    companion object {
        const val MOVE_EXCHANGE = "moveExchange"
        const val MOVE_STORAGE = "moveStorage"
    }
}
