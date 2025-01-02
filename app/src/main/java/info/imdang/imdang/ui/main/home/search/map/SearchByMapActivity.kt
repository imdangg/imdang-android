package info.imdang.imdang.ui.main.home.search.map

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivitySearchByMapBinding

@AndroidEntryPoint
class SearchByMapActivity :
    BaseActivity<ActivitySearchByMapBinding>(R.layout.activity_search_by_map) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListener()
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }
}
