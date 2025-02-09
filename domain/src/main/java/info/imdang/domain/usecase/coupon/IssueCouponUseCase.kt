package info.imdang.domain.usecase.coupon

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.CouponRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class IssueCouponUseCase @Inject constructor(
    private val couponRepository: CouponRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<IssueCouponParams, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: IssueCouponParams) = couponRepository.issueCoupon(
        memberId = parameters.memberId,
        name = parameters.name
    )
}

data class IssueCouponParams(
    val memberId: String,
    val name: String
)
