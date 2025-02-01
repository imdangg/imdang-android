package info.imdang.data.datasource.remote

import info.imdang.data.model.request.coupon.IssueCouponRequest
import info.imdang.data.model.response.coupon.CouponResponse

interface CouponRemoteDataSource {

    suspend fun getCoupon(): CouponResponse

    suspend fun issueCoupon(issueCouponRequest: IssueCouponRequest)
}
