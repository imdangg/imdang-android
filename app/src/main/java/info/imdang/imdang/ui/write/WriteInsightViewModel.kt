package info.imdang.imdang.ui.write

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.common.util.SelectionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WriteInsightViewModel @Inject constructor() : BaseViewModel() {

    private val _isPreviousButtonVisible = MutableStateFlow(false)
    val isPreviousButtonVisible = _isPreviousButtonVisible.asStateFlow()

    private val _selectedPage = MutableStateFlow(0)
    val selectedPage = _selectedPage.asStateFlow()

    private val _isTooltipVisible = MutableStateFlow(false)
    val isTooltipVisible = _isTooltipVisible.asStateFlow()

    // 기본 정보
    private val _coverImageUri = MutableStateFlow<Uri?>(null)
    val coverImageUri = _coverImageUri.asStateFlow()

    private val _isInsightTitleFocused = MutableStateFlow(false)
    val isInsightTitleFocused = _isInsightTitleFocused.asStateFlow()

    private val _isInsightTitleValid = MutableStateFlow(false)
    val isInsightTitleValid = _isInsightTitleValid.asStateFlow()

    private val _isInsightAptAddressValid = MutableStateFlow(false)
    val isInsightAptAddressValid = _isInsightAptAddressValid.asStateFlow()

    private val _isInsightDateFocused = MutableStateFlow(false)
    val isInsightDateFocused = _isInsightDateFocused.asStateFlow()

    private val _isInsightDateValid = MutableStateFlow(false)
    val isInsightDateValid = _isInsightDateValid.asStateFlow()

    private val _insightSelectedTimes = MutableStateFlow<Set<String>>(emptySet())
    val insightSelectedTimes = _insightSelectedTimes.asStateFlow()

    val isInsightTimeCheckImageVisible = insightSelectedTimes.isCheckVisible()

    private val _insightSelectedTraffics = MutableStateFlow<Set<String>>(emptySet())
    val insightSelectedTraffics = _insightSelectedTraffics.asStateFlow()

    val isInsightTrafficCheckImageVisible = insightSelectedTraffics.isCheckVisible()

    private val _insightSelectedEntrances = MutableStateFlow<String?>(null)
    val insightSelectedEntrances = _insightSelectedEntrances.asStateFlow()

    val isInsightEntranceCheckImageVisible = insightSelectedEntrances.isCheckVisible()

    private val _insightSummary = MutableStateFlow("")
    val insightSummary = _insightSummary.asStateFlow()

    val insightSummaryValid = _insightSummary.isValid()

    // 인프라
    val infraTrafficManager = SelectionManager()
    val infraSchoolManager = SelectionManager()
    val infraLivingAmenityManager = SelectionManager()
    val infraFacilitiesManager = SelectionManager()
    val infraEnvironmentManager = SelectionManager()
    val infraLandmarkManager = SelectionManager()
    val infraAvoidFacilityManager = SelectionManager()

    val isInfraTrafficCheckImageVisible = infraTrafficManager.selectedItems.isCheckVisible()

    val isInfraSchoolCheckImageVisible = infraSchoolManager.selectedItems.isCheckVisible()

    val isInfraLivingAmenityCheckImageVisible =
        infraLivingAmenityManager.selectedItems.isCheckVisible()

    val isInfraFacilitiesCheckImageVisible = infraFacilitiesManager.selectedItems.isCheckVisible()

    val isInfraEnvironmentCheckImageVisible = infraEnvironmentManager.selectedItems.isCheckVisible()

    val isInfraLandmarkCheckImageVisible = infraLandmarkManager.selectedItems.isCheckVisible()

    val isInfraAvoidFacilityCheckImageVisible =
        infraAvoidFacilityManager.selectedItems.isCheckVisible()

    private val _infraReview = MutableStateFlow("")
    val infraReview = _infraReview.asStateFlow()

    val infraReviewValid = _infraReview.isValid()

    // 단지 환경
    val complexEnvironmentBuildingManager = SelectionManager(isSingleSelection = true)
    val complexEnvironmentSafetyManager = SelectionManager(isSingleSelection = true)
    val complexEnvironmentChildrenFacilityManager = SelectionManager(isSingleSelection = true)
    val complexEnvironmentSilverFacilityManager = SelectionManager(isSingleSelection = true)

    val isComplexEnvironmentBuildingCheckImageVisible =
        complexEnvironmentBuildingManager.selectedItems.isCheckVisible()

    val isComplexEnvironmentSafetyCheckImageVisible =
        complexEnvironmentSafetyManager.selectedItems.isCheckVisible()

    val isComplexEnvironmentChildrenFacilityCheckImageVisible =
        complexEnvironmentChildrenFacilityManager.selectedItems.isCheckVisible()

    val isComplexEnvironmentSilverFacilityCheckImageVisible =
        complexEnvironmentSilverFacilityManager.selectedItems.isCheckVisible()

    private val _complexEnvironmentReview = MutableStateFlow("")
    val complexEnvironmentReview = _complexEnvironmentReview.asStateFlow()

    val complexEnvironmentReviewValid = _complexEnvironmentReview.isValid()

    // 단지 시설
    val complexFacilityFamilyManager = SelectionManager()
    val complexFacilityMultiPurposeManager = SelectionManager()
    val complexFacilityLeisureManager = SelectionManager()
    val complexFacilityEnvironmentManager = SelectionManager()

    val isComplexFacilityFamilyCheckImageVisible =
        complexFacilityFamilyManager.selectedItems.isCheckVisible()

    val isComplexFacilityMultiPurposeCheckImageVisible =
        complexFacilityMultiPurposeManager.selectedItems.isCheckVisible()

    val isComplexFacilityLeisureCheckImageVisible =
        complexFacilityLeisureManager.selectedItems.isCheckVisible()

    val isComplexFacilityEnvironmentCheckImageVisible =
        complexFacilityEnvironmentManager.selectedItems.isCheckVisible()

    private val _complexFacilityReview = MutableStateFlow("")
    val complexFacilityReview = _complexFacilityReview.asStateFlow()

    val complexFacilityReviewValid = _complexFacilityReview.isValid()

    // 호재
    val goodNewsTrafficManager = SelectionManager()
    val goodNewsDevelopmentManager = SelectionManager()
    val goodNewsEducationManager = SelectionManager()
    val goodNewsNaturalEnvironmentManager = SelectionManager()
    val goodNewsCultureManager = SelectionManager()
    val goodNewsIndustryManager = SelectionManager()
    val goodNewsPolicyManager = SelectionManager()

    val isGoodNewsTrafficCheckImageVisible = goodNewsTrafficManager.selectedItems.isCheckVisible()

    val isGoodNewsDevelopmentCheckImageVisible =
        goodNewsDevelopmentManager.selectedItems.isCheckVisible()

    val isGoodNewsEducationCheckImageVisible =
        goodNewsEducationManager.selectedItems.isCheckVisible()

    val isGoodNewsNaturalEnvironmentCheckImageVisible =
        goodNewsNaturalEnvironmentManager.selectedItems.isCheckVisible()

    val isGoodNewsCultureCheckImageVisible = goodNewsCultureManager.selectedItems.isCheckVisible()

    val isGoodNewsIndustryCheckImageVisible = goodNewsIndustryManager.selectedItems.isCheckVisible()

    val isGoodNewsPolicyCheckImageVisible = goodNewsPolicyManager.selectedItems.isCheckVisible()

    private val _goodNewsReview = MutableStateFlow("")
    val goodNewsReview = _goodNewsReview.asStateFlow()

    val goodNewsReviewValid = _goodNewsReview.isValid()

    private val basicInfoValid = listOf(
        coverImageUri.isCheckVisible(),
        isInsightTitleValid,
        isInsightAptAddressValid,
        isInsightDateValid,
        insightSelectedTimes.isCheckVisible(),
        insightSelectedTraffics.isCheckVisible(),
        insightSelectedEntrances.isCheckVisible(),
        insightSummaryValid
    )

    private val infraValid = listOf(
        infraTrafficManager.selectedItems.isCheckVisible(),
        infraSchoolManager.selectedItems.isCheckVisible(),
        infraLivingAmenityManager.selectedItems.isCheckVisible(),
        infraFacilitiesManager.selectedItems.isCheckVisible(),
        infraEnvironmentManager.selectedItems.isCheckVisible(),
        infraLandmarkManager.selectedItems.isCheckVisible(),
        infraAvoidFacilityManager.selectedItems.isCheckVisible()
    )

    private val complexEnvironmentValid = listOf(
        complexEnvironmentBuildingManager.selectedItems.isCheckVisible(),
        complexEnvironmentSafetyManager.selectedItems.isCheckVisible(),
        complexEnvironmentChildrenFacilityManager.selectedItems.isCheckVisible(),
        complexEnvironmentSilverFacilityManager.selectedItems.isCheckVisible()
    )

    private val complexFacilityValid = listOf(
        complexFacilityFamilyManager.selectedItems.isCheckVisible(),
        complexFacilityMultiPurposeManager.selectedItems.isCheckVisible(),
        complexFacilityLeisureManager.selectedItems.isCheckVisible(),
        complexFacilityEnvironmentManager.selectedItems.isCheckVisible()
    )

    private val goodNewsValid = listOf(
        goodNewsTrafficManager.selectedItems.isCheckVisible(),
        goodNewsDevelopmentManager.selectedItems.isCheckVisible(),
        goodNewsEducationManager.selectedItems.isCheckVisible(),
        goodNewsNaturalEnvironmentManager.selectedItems.isCheckVisible(),
        goodNewsCultureManager.selectedItems.isCheckVisible(),
        goodNewsIndustryManager.selectedItems.isCheckVisible(),
        goodNewsPolicyManager.selectedItems.isCheckVisible()
    )

    fun updateSelectedPage(page: Int) {
        _selectedPage.value = page
        _isPreviousButtonVisible.value = page in 1..3
        updateTooltipVisible(page == 4)
    }

    fun updateTooltipVisible(isVisible: Boolean) {
        _isTooltipVisible.value = isVisible
    }

    fun updateCoverImageUri(uri: Uri?) {
        _coverImageUri.value = uri
    }

    fun updateInsightTitleFocused(isFocused: Boolean) {
        _isInsightTitleFocused.value = isFocused
    }

    fun updateInsightTitleValid(isValid: Boolean) {
        _isInsightTitleValid.value = isValid
    }

    fun updateAptAddressValid(isValid: Boolean) {
        _isInsightAptAddressValid.value = isValid
    }

    fun updateInsightDateFocused(isFocused: Boolean) {
        _isInsightDateFocused.value = isFocused
    }

    fun updateInsightDateValid(isValid: Boolean) {
        _isInsightDateValid.value = isValid
    }

    fun toggleTimeSelection(time: String) {
        val currentSelection = _insightSelectedTimes.value
        _insightSelectedTimes.value = if (currentSelection.contains(time)) {
            currentSelection - time
        } else {
            currentSelection + time
        }
    }

    fun toggleTrafficSelection(traffic: String) {
        val currentSelection = _insightSelectedTraffics.value
        _insightSelectedTraffics.value = if (currentSelection.contains(traffic)) {
            currentSelection - traffic
        } else {
            currentSelection + traffic
        }
    }

    fun selectEntrance(entrance: String) {
        _insightSelectedEntrances.value = entrance
    }

    fun updateInsightSummary(insightSummary: String) {
        _insightSummary.value = insightSummary
    }

    fun updateInfraReview(infraReview: String) {
        _infraReview.value = infraReview
    }

    fun updateComplexEnvironmentReview(complexEnvironmentReview: String) {
        _complexEnvironmentReview.value = complexEnvironmentReview
    }

    fun updateComplexFacilityReview(complexFacility: String) {
        _complexFacilityReview.value = complexFacility
    }

    fun updateGoodNewsReview(goodNews: String) {
        _goodNewsReview.value = goodNews
    }

    fun isFinalButtonEnabled() = combine(
        when (selectedPage.value) {
            0 -> basicInfoValid
            1 -> infraValid
            2 -> complexEnvironmentValid
            3 -> complexFacilityValid
            else -> goodNewsValid
        }
    ) { states ->
        states.all { it }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )
}
