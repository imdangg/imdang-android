package info.imdang.domain.repository

import info.imdang.domain.model.coupon.CouponDto

interface CouponRepository {

    suspend fun getCoupon(): CouponDto

    suspend fun issueCoupon(memberId: String, name: String)
}
