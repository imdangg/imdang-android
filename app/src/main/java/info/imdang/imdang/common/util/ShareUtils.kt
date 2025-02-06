package info.imdang.imdang.common.util

import android.content.ActivityNotFoundException
import android.content.Context
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import info.imdang.imdang.model.insight.InsightDetailVo
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID

fun shareKakao(context: Context, insight: InsightDetailVo) {
    val template = FeedTemplate(
        content = Content(
            title = "${insight.title} | ${insight.address.siGunGu}",
            description = "출처: 아파트 임당 | 인사이트",
            imageUrl = insight.mainImage,
            link = Link(
                androidExecutionParams = mapOf(INSIGHT_ID to insight.insightId)
            )
        ),
        buttons = listOf(
            Button(
                title = "앱으로 보기",
                link = Link(
                    androidExecutionParams = mapOf(INSIGHT_ID to insight.insightId)
                )
            )
        )
    )
    if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
        ShareClient.instance.shareDefault(
            context = context,
            defaultTemplate = template
        ) { linkResult, _ ->
            if (linkResult != null) {
                context.startActivity(linkResult.intent)
            }
        }
    } else {
        val sharerUrl = WebSharerClient.instance.makeDefaultUrl(template = template)

        try {
            KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
        } catch (_: UnsupportedOperationException) {
        }

        try {
            KakaoCustomTabsClient.open(context, sharerUrl)
        } catch (_: ActivityNotFoundException) {
        }
    }
}
