package info.imdang.imdang.common.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2

@BindingAdapter("bindUserInputEnabled")
fun ViewPager2.bindUserInputEnabled(isEnabled: Boolean) {
    isUserInputEnabled = isEnabled
}

@BindingAdapter("bindViewPagerItemList")
fun ViewPager2.bindViewPagerItemList(item: List<Any>?) {
    if (item == null) return

    @Suppress("UNCHECKED_CAST")
    (adapter as? BaseSingleViewAdapter<Any>)?.run {
        submitList(item)
    }
}
