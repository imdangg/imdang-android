package info.imdang.imdang.ui.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.common.util.SelectionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WriteInsightViewModel @Inject constructor() : ViewModel() {

    // 기본 정보
    private val _isInsightTitleFocused = MutableStateFlow(false)
    val isInsightTitleFocused = _isInsightTitleFocused.asStateFlow()

    private val _isInsightTitleValid = MutableStateFlow(false)
    val isInsightTitleValid = _isInsightTitleValid.asStateFlow()

    private val _isInsightTitleCheckImageVisible = MutableStateFlow(false)
    val isInsightTitleCheckImageVisible = _isInsightTitleCheckImageVisible.asStateFlow()

    private val _isInsightAptAddressValid = MutableStateFlow(false)
    val isInsightAptAddressValid = _isInsightAptAddressValid.asStateFlow()

    private val _isInsightDateFocused = MutableStateFlow(false)
    val isInsightDateFocused = _isInsightDateFocused.asStateFlow()

    private val _isInsightDateValid = MutableStateFlow(false)
    val isInsightDateValid = _isInsightDateValid.asStateFlow()

    private val _isInsightDateCheckImageVisible = MutableStateFlow(false)
    val isInsightDateCheckImageVisible = _isInsightDateCheckImageVisible.asStateFlow()

    private val _insightSelectedTimes = MutableStateFlow<Set<String>>(emptySet())
    val insightSelectedTimes = _insightSelectedTimes.asStateFlow()

    val isInsightTimeCheckImageVisible = insightSelectedTimes.map { it.isNotEmpty() }
        .asLiveData()

    private val _insightSelectedTraffics = MutableStateFlow<Set<String>>(emptySet())
    val insightSelectedTraffics = _insightSelectedTraffics.asStateFlow()

    val isInsightTrafficCheckImageVisible = insightSelectedTraffics.map { it.isNotEmpty() }
        .asLiveData()

    private val _insightSelectedEntrances = MutableStateFlow<String?>(null)
    val insightSelectedEntrances = _insightSelectedEntrances.asStateFlow()

    val isInsightEntranceCheckImageVisible = insightSelectedEntrances.map { it != null }
        .asLiveData()

    // 인프라
    val infraTrafficManager = SelectionManager()
    val infraSchoolManager = SelectionManager()
    val infraLivingAmenityManager = SelectionManager()
    val infraFacilitiesManager = SelectionManager()
    val infraEnvironmentManager = SelectionManager()
    val infraLandmarkManager = SelectionManager()
    val infraAvoidFacilityManager = SelectionManager()

    val isInfraTrafficCheckImageVisible = infraTrafficManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isInfraSchoolCheckImageVisible = infraSchoolManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isInfraLivingAmenityCheckImageVisible = infraLivingAmenityManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isInfraFacilitiesCheckImageVisible = infraFacilitiesManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isInfraEnvironmentCheckImageVisible = infraEnvironmentManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isInfraLandmarkCheckImageVisible = infraLandmarkManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isInfraAvoidFacilityCheckImageVisible = infraAvoidFacilityManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    // 단지 환경
    val complexEnvironmentBuildingManager = SelectionManager(isSingleSelection = true)
    val complexEnvironmentSafetyManager = SelectionManager(isSingleSelection = true)
    val complexEnvironmentChildrenFacilityManager = SelectionManager(isSingleSelection = true)
    val complexEnvironmentSilverFacilityManager = SelectionManager(isSingleSelection = true)

    val isComplexEnvironmentBuildingCheckImageVisible =
        complexEnvironmentBuildingManager.selectedItems
            .map { it.isNotEmpty() }
            .asLiveData()

    val isComplexEnvironmentSafetyCheckImageVisible = complexEnvironmentSafetyManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isComplexEnvironmentChildrenFacilityCheckImageVisible =
        complexEnvironmentChildrenFacilityManager.selectedItems
            .map { it.isNotEmpty() }
            .asLiveData()

    val isComplexEnvironmentSilverFacilityCheckImageVisible =
        complexEnvironmentSilverFacilityManager.selectedItems
            .map { it.isNotEmpty() }
            .asLiveData()

    // 단지 시설
    val complexFacilityFamilyManager = SelectionManager()
    val complexFacilityMultiPurposeManager = SelectionManager()
    val complexFacilityLeisureManager = SelectionManager()
    val complexFacilityEnvironmentManager = SelectionManager()

    val isComplexFacilityFamilyCheckImageVisible = complexFacilityFamilyManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isComplexFacilityMultiPurposeCheckImageVisible =
        complexFacilityMultiPurposeManager.selectedItems
            .map { it.isNotEmpty() }
            .asLiveData()

    val isComplexFacilityLeisureCheckImageVisible = complexFacilityLeisureManager.selectedItems
        .map { it.isNotEmpty() }
        .asLiveData()

    val isComplexFacilityEnvironmentCheckImageVisible =
        complexFacilityEnvironmentManager.selectedItems
            .map { it.isNotEmpty() }
            .asLiveData()

    private val allValidationStates = listOf(
        isInsightTitleValid,
        isInsightAptAddressValid,
        isInsightDateValid,
        insightSelectedTimes.map { it.isNotEmpty() },
        insightSelectedTraffics.map { it.isNotEmpty() },
        insightSelectedEntrances.map { it != null },
        infraTrafficManager.selectedItems.map { it.isNotEmpty() },
        infraSchoolManager.selectedItems.map { it.isNotEmpty() },
        infraLivingAmenityManager.selectedItems.map { it.isNotEmpty() },
        infraFacilitiesManager.selectedItems.map { it.isNotEmpty() },
        infraEnvironmentManager.selectedItems.map { it.isNotEmpty() },
        infraLandmarkManager.selectedItems.map { it.isNotEmpty() },
        infraAvoidFacilityManager.selectedItems.map { it.isNotEmpty() },
        complexEnvironmentBuildingManager.selectedItems.map { it.isNotEmpty() },
        complexEnvironmentSafetyManager.selectedItems.map { it.isNotEmpty() },
        complexEnvironmentChildrenFacilityManager.selectedItems.map { it.isNotEmpty() },
        complexEnvironmentSilverFacilityManager.selectedItems.map { it.isNotEmpty() },
        complexFacilityFamilyManager.selectedItems.map { it.isNotEmpty() },
        complexFacilityMultiPurposeManager.selectedItems.map { it.isNotEmpty() },
        complexFacilityLeisureManager.selectedItems.map { it.isNotEmpty() },
        complexFacilityEnvironmentManager.selectedItems.map { it.isNotEmpty() }
    )

    val isFinalButtonEnabled = combine(allValidationStates) { states ->
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

    fun updateInsightTitleCheckImageVisible(isVisible: Boolean) {
        _isInsightTitleCheckImageVisible.value = isVisible
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

    fun updateInsightDateCheckImageVisible(isVisible: Boolean) {
        _isInsightDateCheckImageVisible.value = isVisible
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
