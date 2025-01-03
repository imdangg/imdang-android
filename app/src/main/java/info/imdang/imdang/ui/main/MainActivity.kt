package info.imdang.imdang.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.startActivity
import info.imdang.imdang.databinding.ActivityMainBinding
import info.imdang.imdang.ui.main.home.HomeFragment
import info.imdang.imdang.ui.main.storage.StorageFragment
import info.imdang.imdang.ui.write.WriteInsightActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var homeFragment: HomeFragment
    private lateinit var storageFragment: StorageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupFragment()
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
                startActivity<WriteInsightActivity>()
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
}
