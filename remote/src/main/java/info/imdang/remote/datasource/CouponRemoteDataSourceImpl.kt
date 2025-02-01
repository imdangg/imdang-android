package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.CouponRemoteDataSource
import info.imdang.data.model.response.coupon.CouponResponse
import info.imdang.data.model.request.coupon.IssueCouponRequest
import info.imdang.remote.service.CouponService
import javax.inject.Inject

internal class CouponRemoteDataSourceImpl @Inject constructor(
    private val couponService: CouponService
) : CouponRemoteDataSource {

    override suspend fun getCoupon(): CouponResponse = couponService.getCoupon()

    override suspend fun issueCoupon(issueCouponRequest: IssueCouponRequest) =
        couponService.issueCoupon(issueCouponRequest)
}
