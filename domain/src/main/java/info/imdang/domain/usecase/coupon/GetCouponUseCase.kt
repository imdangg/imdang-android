package info.imdang.domain.usecase.coupon

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.coupon.CouponDto
import info.imdang.domain.repository.CouponRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCouponUseCase @Inject constructor(
    private val couponRepository: CouponRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, CouponDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): CouponDto = couponRepository.getCoupon()
}
