package info.imdang.remote.service

import info.imdang.data.model.response.coupon.CouponResponse
import retrofit2.http.GET

internal interface CouponService {

    /** 아파트 단지 이름 목록 조회 */
    @GET("coupons")
    suspend fun getCoupon(): CouponResponse
}
