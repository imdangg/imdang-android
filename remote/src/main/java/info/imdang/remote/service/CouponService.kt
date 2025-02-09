package info.imdang.remote.service

import info.imdang.data.model.request.coupon.IssueCouponRequest
import retrofit2.http.Body
import retrofit2.http.POST

internal interface CouponService {

    /** 쿠폰 발행 API */
    @POST("coupons/issue")
    suspend fun issueCoupon(
        @Body issueCouponRequest: IssueCouponRequest
    )
}
