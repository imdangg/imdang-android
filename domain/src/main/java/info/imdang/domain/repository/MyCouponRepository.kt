package info.imdang.domain.repository

import info.imdang.domain.model.coupon.CouponDto

interface MyCouponRepository {

    suspend fun getCoupon(): CouponDto
}
