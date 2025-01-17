package info.imdang.imdang.common.bindingadapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import info.imdang.imdang.common.ext.dpToPx
import info.imdang.imdang.model.insight.InsightDetailState
import java.io.File

@BindingAdapter(
    value = ["bindImage", "bindCornerRadius", "bindPlaceHolder"],
    requireAll = false
)
fun ImageView.bindImage(
    url: String?,
    cornerRadius: Int = 0,
    placeHolder: Drawable? = null
) {
    val requestOptions = if (cornerRadius > 0) {
        RequestOptions().transform(CenterCrop(), RoundedCorners(context.dpToPx(cornerRadius)))
    } else {
        RequestOptions().transform(CenterCrop())
    }
    Glide.with(context)
        .load(url)
        .apply(
            requestOptions
                .error(placeHolder)
                .placeholder(placeHolder)
        )
        .into(this)
}

@BindingAdapter(
    value = ["bindImageFile", "bindCornerRadius", "bindPlaceHolder"],
    requireAll = false
)
fun ImageView.bindImageFile(
    file: File?,
    cornerRadius: Int = 0,
    placeHolder: Drawable? = null
) {
    val requestOptions = if (cornerRadius > 0) {
        RequestOptions().transform(CenterCrop(), RoundedCorners(context.dpToPx(cornerRadius)))
    } else {
        RequestOptions().transform(CenterCrop())
    }
    Glide.with(context)
        .load(file)
        .apply(
            requestOptions
                .error(placeHolder)
                .placeholder(placeHolder)
        )
        .into(this)
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("bindInsightDetailInvisibleImage")
fun ImageView.bindInsightDetailInvisibleImage(insightDetailState: InsightDetailState) {
    setImageDrawable(
        context.getDrawable(
            when (insightDetailState) {
                InsightDetailState.EXCHANGE_REQUEST -> {
                    info.imdang.component.R.drawable.ic_insight_detail_invisible_request
                }
                InsightDetailState.EXCHANGE_REQUESTED -> {
                    info.imdang.component.R.drawable.ic_insight_detail_invisible_requested
                }
                InsightDetailState.EXCHANGE_WAITING -> {
                    info.imdang.component.R.drawable.ic_insight_detail_invisible_waiting
                }
                else -> -1
            }
        )
    )
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
