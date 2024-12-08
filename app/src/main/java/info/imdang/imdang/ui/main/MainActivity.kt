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
        setupFragment()
    }

    private fun setupBinding() {
        with(binding) {
            viewModel = this@MainActivity.viewModel
            fabMain.setOnClickListener {
                startActivity<WriteInsightActivity>()
            }
            bnvMain.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> replaceFragment(homeFragment)
                    R.id.menu_storage -> replaceFragment(storageFragment)
                }
                true
            }
        }
    }

    private fun setupFragment() {
        homeFragment = HomeFragment()
        storageFragment = StorageFragment()
        replaceFragment(homeFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(binding.flMain.id, fragment)
            commit()
        }
    }
}
