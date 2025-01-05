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

        setupListener()
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            clPrivacyTerm.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.notion.so/4d557e465d6143a3abc133397966c3d1?pvs=4")
                    )
                )
            }
            clUseTerm.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.notion.so/54dd2a7ccd5a4c8193e06df782d02119?pvs=4")
                    )
                )
            }
        }
    }
}
