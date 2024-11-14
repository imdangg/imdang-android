package info.imdang.imdang.common.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import info.imdang.imdang.common.ext.dpToPx

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
