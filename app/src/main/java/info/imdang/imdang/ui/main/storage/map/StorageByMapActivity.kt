package info.imdang.imdang.ui.main.storage.map

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityStorageByMapBinding

@AndroidEntryPoint
class StorageByMapActivity :
    BaseActivity<ActivityStorageByMapBinding>(R.layout.activity_storage_by_map) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("지도로 찾기")
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
