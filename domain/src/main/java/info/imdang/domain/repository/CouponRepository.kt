package info.imdang.domain.repository

interface CouponRepository {

    suspend fun getCouponCount(): Int

    suspend fun issueCoupon(memberId: String, couponId: String)
}
