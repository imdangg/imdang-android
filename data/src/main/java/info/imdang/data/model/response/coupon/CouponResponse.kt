package info.imdang.data.model.response.coupon

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.coupon.CouponDto

data class CouponResponse(
    val couponCount: Int,
    val memberCouponId: Long?
) : DataToDomainMapper<CouponDto> {
    override fun mapper(): CouponDto = CouponDto(
        couponCount = couponCount,
        memberCouponId = memberCouponId
    )
}
