package info.imdang.imdang.ui.login.onboarding

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.ui.login.onboarding.OnboardingFragment.Companion.ONBOARDING_POSITION
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val content = listOf(
        "가이드로 인사이트 작성하기" to "가이드라인으로 체계화된 인사이트를\n간편하게 작성할 수 있어요",
        "인사이트 교환하기" to "양질의 인사이트를 주고받으며\n가치 있는 임장 인사이트를 교환하세요",
        "다양한 인사이트 모으기" to "작성한 인사이트와 교환한 인사이트를\n보관함에서 편리하게 관리하세요"
    )[savedStateHandle[ONBOARDING_POSITION] ?: 0]
}
