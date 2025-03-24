package info.imdang.domain.usecase.coupon

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.CouponRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class IssueCouponUseCase @Inject constructor(
    private val couponRepository: CouponRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit) = couponRepository.issueCoupon()
}
