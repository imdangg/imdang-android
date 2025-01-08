package info.imdang.imdang.ui.write.address

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.BuildConfig
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.databinding.ActivityKakaoAddressBinding
import org.json.JSONException
import org.json.JSONObject

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
                val jsonObject = JSONObject(address)
                val roadAddress = jsonObject.optString("roadAddress", "")
                val buildingName = jsonObject.optString("buildingName", "")

                val intent = Intent().apply {
                    putExtra("roadAddress", roadAddress)
                    putExtra("aptName", buildingName)
                }

                setResult(RESULT_OK, intent)
                finish()
            } catch (e: JSONException) {
                Log.e("imdang", "JSON 파싱 오류: ${e.message}")
            }
        }
    }
}
