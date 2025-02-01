package info.imdang.remote.service

import info.imdang.data.model.response.coupon.CouponResponse
import info.imdang.data.model.request.coupon.IssueCouponRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface CouponService {

    /** 아파트 단지 이름 목록 조회 */
    @GET("coupons")
    suspend fun getCoupon(): CouponResponse

    /** 쿠폰 발행 API */
    @POST("coupons/issue")
    suspend fun issueCoupon(
        @Body issueCouponRequest: IssueCouponRequest
    )
}
