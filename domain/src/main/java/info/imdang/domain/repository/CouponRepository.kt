package info.imdang.domain.repository

interface CouponRepository {

    suspend fun issueCoupon(memberId: String, name: String)
}
