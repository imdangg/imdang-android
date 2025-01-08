package info.imdang.imdang.ui.write

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

    // 기본 정보
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

    val isFinalButtonEnabled = combine(
        listOf(
            isInsightTitleValid,
            isInsightAptAddressValid,
            isInsightDateValid,
            insightSelectedTimes.isCheckVisible(),
            insightSelectedTraffics.isCheckVisible(),
            insightSelectedEntrances.isCheckVisible(),
            infraTrafficManager.selectedItems.isCheckVisible(),
            infraSchoolManager.selectedItems.isCheckVisible(),
            infraLivingAmenityManager.selectedItems.isCheckVisible(),
            infraFacilitiesManager.selectedItems.isCheckVisible(),
            infraEnvironmentManager.selectedItems.isCheckVisible(),
            infraLandmarkManager.selectedItems.isCheckVisible(),
            infraAvoidFacilityManager.selectedItems.isCheckVisible(),
            complexEnvironmentBuildingManager.selectedItems.isCheckVisible(),
            complexEnvironmentSafetyManager.selectedItems.isCheckVisible(),
            complexEnvironmentChildrenFacilityManager.selectedItems.isCheckVisible(),
            complexEnvironmentSilverFacilityManager.selectedItems.isCheckVisible(),
            complexFacilityFamilyManager.selectedItems.isCheckVisible(),
            complexFacilityMultiPurposeManager.selectedItems.isCheckVisible(),
            complexFacilityLeisureManager.selectedItems.isCheckVisible(),
            complexFacilityEnvironmentManager.selectedItems.isCheckVisible(),
            goodNewsTrafficManager.selectedItems.isCheckVisible(),
            goodNewsDevelopmentManager.selectedItems.isCheckVisible(),
            goodNewsEducationManager.selectedItems.isCheckVisible(),
            goodNewsNaturalEnvironmentManager.selectedItems.isCheckVisible(),
            goodNewsCultureManager.selectedItems.isCheckVisible(),
            goodNewsIndustryManager.selectedItems.isCheckVisible(),
            goodNewsPolicyManager.selectedItems.isCheckVisible()
        )
    ) { states ->
        states.all { it }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

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
}
