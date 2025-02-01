package info.imdang.imdang.model.coupon

import info.imdang.domain.model.coupon.CouponDto

data class CouponVo(
    val couponCount: Int,
    val couponId: Long?
) {
    companion object {
        fun init() = CouponVo(
            couponCount = 0,
            couponId = null
        )
    }
}

fun CouponDto.mapper(): CouponVo = CouponVo(
    couponCount = couponCount,
    couponId = memberCouponId
)
