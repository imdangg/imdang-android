package info.imdang.imdang.ui.insight

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import info.imdang.imdang.BR
import info.imdang.imdang.common.bindingadapter.ViewHolderType
import info.imdang.imdang.common.ext.dpToPx
import info.imdang.imdang.databinding.ItemInsightDetailBasicInfoBinding
import info.imdang.imdang.model.insight.InsightDetailItem
import kotlin.reflect.KClass

class InsightDetailAdapter(
    private val viewHolderMapper: (InsightDetailItem) -> ViewHolderType = {
        when (it) {
            is InsightDetailItem.BasicInfo -> InsightDetailHolderType.BasicInfoHolder
            is InsightDetailItem.Infra -> InsightDetailHolderType.InfraHolder
            is InsightDetailItem.AptEnvironment -> {
                InsightDetailHolderType.AptEnvironmentHolder
            }
            is InsightDetailItem.AptFacility -> {
                InsightDetailHolderType.AptFacilityHolder
            }
            is InsightDetailItem.GoodNews -> InsightDetailHolderType.GoodNewsHolder
            is InsightDetailItem.Invisible -> InsightDetailHolderType.InvisibleHolder
        }
    },
    private val viewHolderType: KClass<out ViewHolderType> = InsightDetailHolderType::class,
    private val viewModel: InsightDetailViewModel
) : ListAdapter<InsightDetailItem, InsightDetailViewHolder>(
        object : DiffUtil.ItemCallback<InsightDetailItem>() {
            override fun areItemsTheSame(
                oldItem: InsightDetailItem,
                newItem: InsightDetailItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: InsightDetailItem,
                newItem: InsightDetailItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsightDetailViewHolder {
        val viewHolderType = viewHolderType.java.enumConstants[viewType]
        return InsightDetailViewHolder(
            parent = parent,
            layoutResourceId = viewHolderType.layoutResourceId,
            bindingItemId = viewHolderType.bindingItemId,
            viewModel = viewModel
        )
    }

    override fun getItemViewType(position: Int): Int =
        (viewHolderMapper(getItem(position)) as Enum<*>).ordinal

    override fun onBindViewHolder(holder: InsightDetailViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class InsightDetailViewHolder(
    private val parent: ViewGroup,
    @LayoutRes private val layoutResourceId: Int,
    private val bindingItemId: Int,
    private val viewModel: InsightDetailViewModel
) : RecyclerView.ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(layoutResourceId, parent, false)
    ) {
    private val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

    fun bind(item: Any) {
        with(binding) {
            setVariable(bindingItemId, item)
            setVariable(BR.viewModel, viewModel)
            executePendingBindings()
            if (this is ItemInsightDetailBasicInfoBinding && item is InsightDetailItem.BasicInfo) {
                mapInsightDetail.getMapAsync {
                    setupMap(naverMap = it, item = item)
                }
            }
        }
    }

    private fun setupMap(naverMap: NaverMap, item: InsightDetailItem.BasicInfo) {
        with(naverMap) {
            with(uiSettings) {
                locationTrackingMode = LocationTrackingMode.None
                isLocationButtonEnabled = false
                isZoomControlEnabled = false
                isZoomGesturesEnabled = false
                isLogoClickEnabled = false
                isTiltGesturesEnabled = false
                isZoomGesturesEnabled = false
                isScrollGesturesEnabled = false
                isRotateGesturesEnabled = false
            }
            moveCamera(
                CameraUpdate.toCameraPosition(
                    CameraPosition(
                        LatLng(
                            item.latitude + 0.0003,
                            item.longitude
                        ),
                        14.5
                    )
                )
            )
            Marker().apply {
                position = LatLng(item.latitude, item.longitude)
                icon = OverlayImage.fromResource(info.imdang.component.R.drawable.ic_pin)
                width = parent.context.dpToPx(32)
                height = parent.context.dpToPx(32)
                map = naverMap
            }
            setOnMapClickListener { _, _ ->
                val name = item.address
                    .split("\n")
                    .last()
                    .replace("(", "")
                    .replace(")", "")
                val naverMapUrl = "nmap://place" +
                    "?lat=${item.latitude}" +
                    "&lng=${item.longitude}" +
                    "&name=$name" +
                    "&appname=${parent.context.packageName}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(naverMapUrl)).apply {
                    addCategory(Intent.CATEGORY_BROWSABLE)
                }

                val list = parent.context.packageManager.queryIntentActivities(
                    intent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
                if (list.isEmpty()) {
                    val naverMapWebUrl = "https://map.naver.com?lat=${item.latitude}" +
                        "&lng=${item.longitude}" +
                        "&title=$name"
                    parent.context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(naverMapWebUrl))
                    )
                } else {
                    parent.context.startActivity(intent)
                }
            }
        }
    }
}
