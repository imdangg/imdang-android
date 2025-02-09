package info.imdang.data.datasource.remote

import info.imdang.data.model.response.coupon.CouponResponse

interface MyCouponRemoteDataSource {

    suspend fun getCoupon(): CouponResponse
}
