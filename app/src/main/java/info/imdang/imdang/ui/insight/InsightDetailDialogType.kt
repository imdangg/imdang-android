package info.imdang.imdang.ui.insight

import androidx.annotation.DrawableRes
import info.imdang.component.R

enum class InsightDetailDialogType(
    @DrawableRes val icon: Int,
    val message: String,
    val subButtonText: String? = null
) {
    RECOMMEND_INFO(
        icon = R.drawable.ic_sign_for_dialog,
        message = "인사이트 추천은 교환 후 가능해요"
    ),
    EXCHANGE_INFO(
        icon = R.drawable.ic_check_for_dialog,
        message = "교환할 인사이트가 없어요.\n" +
            "임장을 다녀온 후 인사이트를\n" +
            "작성해주세요."
    ),
    EXCHANGE_REQUEST(
        icon = R.drawable.ic_check_for_dialog,
        message = "교환 요청을 완료했어요.\n" +
            "교환 내역은 교환소에서 확인해보세요.",
        subButtonText = "교환소 확인하기"
    ),
    EXCHANGE_ACCEPT(
        icon = R.drawable.ic_check_for_dialog,
        message = "교환을 수락했어요.\n" +
            "교환한 인사이트는 보관함에서\n" +
            "확인할 수 있어요.",
        subButtonText = "보관함 확인하기"
    ),
    EXCHANGE_REJECT(
        icon = R.drawable.ic_check_for_dialog,
        message = "교환을 거절했어요."
    )
}
