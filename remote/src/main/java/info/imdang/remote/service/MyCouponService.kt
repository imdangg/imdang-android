package info.imdang.remote.service

import info.imdang.data.model.response.coupon.CouponResponse
import retrofit2.http.GET

internal interface MyCouponService {

    /** 쿠폰 개수 조회 */
    @GET("my-coupons/detail")
    suspend fun getCoupon(): CouponResponse
}
