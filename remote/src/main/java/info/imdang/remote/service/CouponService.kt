package info.imdang.remote.service

import retrofit2.http.GET

internal interface CouponService {

    /** 아파트 단지 이름 목록 조회 */
    @GET("coupons")
    suspend fun getCouponCount(): Int
}
