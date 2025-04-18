package info.imdang.imdang.ui.my.term

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityServiceTermBinding

@AndroidEntryPoint
class ServiceTermActivity :
    BaseActivity<ActivityServiceTermBinding>(R.layout.activity_service_term) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("서비스 이용 약관")
        setupListener()
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            clPrivacyTerm.setOnClickListener {
                val url = "https://principled-cry-2aa.notion.site/4d557e465d6143a3abc133397966c3d1"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
            clUseTerm.setOnClickListener {
                val url = "https://principled-cry-2aa.notion.site/54dd2a7ccd5a4c8193e06df782d02119"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    }
}
