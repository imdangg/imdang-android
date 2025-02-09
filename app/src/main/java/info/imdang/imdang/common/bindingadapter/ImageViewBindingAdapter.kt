package info.imdang.imdang.common.bindingadapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import info.imdang.imdang.common.ext.dpToPx
import info.imdang.imdang.model.insight.InsightDetailStatus

@BindingAdapter(
    value = ["bindImage", "bindCornerRadius", "bindPlaceHolder"],
    requireAll = false
)
fun ImageView.bindImage(
    image: Any?,
    cornerRadius: Int = 0,
    placeHolder: Drawable? = null
) {
    val requestOptions = if (cornerRadius > 0) {
        RequestOptions().transform(CenterCrop(), RoundedCorners(context.dpToPx(cornerRadius)))
    } else {
        RequestOptions().transform(CenterCrop())
    }
    Glide.with(context)
        .load(image)
        .apply(
            requestOptions
                .error(placeHolder)
                .placeholder(placeHolder)
        )
        .into(this)
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("bindInsightDetailInvisibleImage")
fun ImageView.bindInsightDetailInvisibleImage(insightDetailStatus: InsightDetailStatus) {
    setImageDrawable(
        context.getDrawable(
            when (insightDetailStatus) {
                InsightDetailStatus.EXCHANGE_REQUEST -> {
                    info.imdang.component.R.drawable.ic_insight_detail_invisible_request
                }
                InsightDetailStatus.EXCHANGE_REQUESTED -> {
                    info.imdang.component.R.drawable.ic_insight_detail_invisible_requested
                }
                InsightDetailStatus.EXCHANGE_WAITING -> {
                    info.imdang.component.R.drawable.ic_insight_detail_invisible_waiting
                }
                else -> -1
            }
        )
    )
}

@BindingAdapter("bindTint")
fun ImageView.bindTint(
    @ColorRes color: Int
) {
    imageTintList = ColorStateList.valueOf(context.getColor(color))
}

@BindingAdapter(
    value = ["bindSelectedPage", "bindCurrentPage"],
    requireAll = true
)
fun ImageView.bindWriteInsightStepImage(
    selectedPage: Int,
    currentPage: Int
) {
    setImageResource(
        when (selectedPage) {
            currentPage -> info.imdang.component.R.drawable.ic_step
            else -> info.imdang.component.R.drawable.ic_check
        }
    )
    imageTintList = if (selectedPage < currentPage) {
        ColorStateList.valueOf(context.getColor(info.imdang.component.R.color.gray_200))
    } else {
        null
    }
}

@BindingAdapter("bindImageResource")
fun ImageView.bindImageResource(resource: Int) {
    this.setImageResource(resource)
}
