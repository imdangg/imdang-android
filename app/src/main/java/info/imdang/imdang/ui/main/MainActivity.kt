package info.imdang.imdang.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityMainBinding
import info.imdang.imdang.ui.main.home.HomeFragment
import info.imdang.imdang.ui.main.storage.StorageFragment
import info.imdang.imdang.ui.write.WriteInsightActivity
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
                    MainEvent.MoveStorage -> binding.bnvMain.selectedItemId = R.id.menu_storage
                }
            }
        }
    }
}
