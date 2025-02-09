package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.MyCouponRemoteDataSource
import info.imdang.data.model.response.coupon.CouponResponse
import info.imdang.remote.service.MyCouponService
import javax.inject.Inject

internal class MyCouponRemoteDataSourceImpl @Inject constructor(
    private val myCouponService: MyCouponService
) : MyCouponRemoteDataSource {

    override suspend fun getCoupon(): CouponResponse = myCouponService.getCoupon()
}
