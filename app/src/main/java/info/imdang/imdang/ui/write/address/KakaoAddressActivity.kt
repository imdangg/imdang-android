package info.imdang.imdang.ui.write.address

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.BuildConfig
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityKakaoAddressBinding
import info.imdang.imdang.model.address.KakaoAddressVo
import org.json.JSONException

@AndroidEntryPoint
class KakaoAddressActivity :
    BaseActivity<ActivityKakaoAddressBinding>(R.layout.activity_kakao_address) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupWebView()
    }

    private fun setupWebView() {
        val webView = binding.wvKakao
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.clearHistory()
        webView.clearCache(true)

        webView.addJavascriptInterface(WebViewInterface(), "Android")

        webView.loadUrl(BuildConfig.KAKAO_ADDRESS_SEARCH_SERVER)
    }

    inner class WebViewInterface {

        @JavascriptInterface
        fun processDATA(address: String) {
            try {
                val kakaoAddressVo = Gson().fromJson(address, KakaoAddressVo::class.java)
                val coder = Geocoder(this@KakaoAddressActivity)
                coder.getFromLocationName(address, 1)?.let {
                    val intent = Intent().apply {
                        putExtra(APT_ADDRESS, kakaoAddressVo.jibunAddress)
                        putExtra(APT_NAME, kakaoAddressVo.buildingName)
                        putExtra(LATITUDE, it[0].latitude)
                        putExtra(LONGITUDE, it[0].longitude)
                    }

                    setResult(RESULT_OK, intent)
                    finish()
                }
            } catch (e: JSONException) {
                Log.e("imdang", "JSON 파싱 오류: ${e.message}")
            }
        }
    }

    companion object {
        const val APT_ADDRESS = "aptAddress"
        const val APT_NAME = "aptName"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }
}
