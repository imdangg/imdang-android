package info.imdang.data.repository

import info.imdang.data.datasource.remote.CouponRemoteDataSource
import info.imdang.domain.repository.CouponRepository
import javax.inject.Inject

internal class CouponRepositoryImpl @Inject constructor(
    private val couponRemoteDataSource: CouponRemoteDataSource
) : CouponRepository {

    override suspend fun issueCoupon() = couponRemoteDataSource.issueCoupon()
}
