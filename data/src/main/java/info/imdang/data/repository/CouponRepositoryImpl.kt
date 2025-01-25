package info.imdang.data.repository

import info.imdang.data.datasource.remote.CouponRemoteDataSource
import info.imdang.data.model.request.coupon.IssueCouponRequest
import info.imdang.domain.repository.CouponRepository
import javax.inject.Inject

internal class CouponRepositoryImpl @Inject constructor(
    private val couponRemoteDataSource: CouponRemoteDataSource
) : CouponRepository {

    override suspend fun getCouponCount(): Int = couponRemoteDataSource.getCouponCount()

    override suspend fun issueCoupon(
        memberId: String,
        couponId: String
    ) = couponRemoteDataSource.issueCoupon(
        IssueCouponRequest(
            memberId = memberId,
            couponId = couponId
        )
    )
}
