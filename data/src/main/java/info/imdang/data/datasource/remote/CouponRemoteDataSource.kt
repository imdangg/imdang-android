package info.imdang.data.datasource.remote

import info.imdang.data.model.response.coupon.CouponResponse

interface CouponRemoteDataSource {

    suspend fun getCoupon(): CouponResponse
}
