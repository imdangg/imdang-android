package info.imdang.domain.repository

interface CouponRepository {

    suspend fun getCouponCount(): Int
}
