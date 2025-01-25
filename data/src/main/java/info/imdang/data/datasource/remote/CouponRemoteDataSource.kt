package info.imdang.data.datasource.remote

import info.imdang.data.model.request.coupon.IssueCouponRequest

interface CouponRemoteDataSource {

    suspend fun getCouponCount(): Int

    suspend fun issueCoupon(issueCouponRequest: IssueCouponRequest)
}
