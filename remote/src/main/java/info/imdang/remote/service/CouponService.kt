package info.imdang.remote.service

import retrofit2.http.POST

internal interface CouponService {

    /** 쿠폰 발행 API */
    @POST("coupons/issue")
    suspend fun issueCoupon()
}
