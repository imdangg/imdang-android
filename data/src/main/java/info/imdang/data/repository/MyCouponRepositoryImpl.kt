package info.imdang.data.repository

import info.imdang.data.datasource.remote.MyCouponRemoteDataSource
import info.imdang.domain.model.coupon.CouponDto
import info.imdang.domain.repository.MyCouponRepository
import javax.inject.Inject

internal class MyCouponRepositoryImpl @Inject constructor(
    private val myCouponRemoteDataSource: MyCouponRemoteDataSource
) : MyCouponRepository {

    override suspend fun getCoupon(): CouponDto = myCouponRemoteDataSource.getCoupon().mapper()
}
