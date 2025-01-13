package info.imdang.imdang.ui.write

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.insight.request.AddressDto
import info.imdang.domain.model.insight.request.ApartmentComplexDto
import info.imdang.domain.model.insight.request.ComplexEnvironmentDto
import info.imdang.domain.model.insight.request.ComplexFacilityDto
import info.imdang.domain.model.insight.request.FavorableNewsDto
import info.imdang.domain.model.insight.request.InfraDto
import info.imdang.domain.model.insight.request.MultiChoiceDto
import info.imdang.domain.model.insight.request.SingleChoiceDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.usecase.insight.WriteInsightUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.common.util.SelectionManager
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteInsightViewModel @Inject constructor(
    private val writeInsightUseCase: WriteInsightUseCase
) : BaseViewModel() {

    private val _progress = MutableStateFlow("00%")
    val progress = _progress.asStateFlow()

    private val _isPreviousButtonVisible = MutableStateFlow(false)
    val isPreviousButtonVisible = _isPreviousButtonVisible.asStateFlow()

    private val _selectedPage = MutableStateFlow(0)
    val selectedPage = _selectedPage.asStateFlow()

    private val _isTooltipVisible = MutableStateFlow(false)
    val isTooltipVisible = _isTooltipVisible.asStateFlow()

    // 기본 정보
    private val _coverImageUri = MutableStateFlow<Uri?>(null)
    val coverImageUri = _coverImageUri.asStateFlow()

    private val _insightTitle = MutableStateFlow("")
    private val insightTitle = _insightTitle.asStateFlow()

    private val _isInsightTitleFocused = MutableStateFlow(false)
    val isInsightTitleFocused = _isInsightTitleFocused.asStateFlow()

    private val _isInsightTitleValid = MutableStateFlow(false)
    val isInsightTitleValid = _isInsightTitleValid.asStateFlow()

    private val _insightAptAddress = MutableStateFlow("")
    val insightAptAddress = _insightAptAddress.asStateFlow()

    private val _insightAptName = MutableStateFlow("")
    val insightAptName = _insightAptName.asStateFlow()

    private val _isInsightAptAddressValid = MutableStateFlow(false)
    val isInsightAptAddressValid = _isInsightAptAddressValid.asStateFlow()

    private val _insightVisitDate = MutableStateFlow("")
    private val insightVisitDate = _insightVisitDate.asStateFlow()

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

    fun updateProgress() {
        var progress = if (basicInfoValid.all { it.value }) 20 else 0
        progress += if (infraValid.all { it.value }) 10 else 0
        progress += if (infraReviewValid.value) 10 else 0
        progress += if (complexEnvironmentValid.all { it.value }) 10 else 0
        progress += if (complexEnvironmentReviewValid.value) 10 else 0
        progress += if (complexFacilityValid.all { it.value }) 10 else 0
        progress += if (complexFacilityReviewValid.value) 10 else 0
        progress += if (goodNewsValid.all { it.value }) 10 else 0
        progress += if (goodNewsReviewValid.value) 10 else 0
        _progress.value = "${String.format(Locale.KOREA, "%02d", progress)}%"
    }

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

    fun updateInsightTitle(title: String) {
        _insightTitle.value = title
    }

    fun updateInsightTitleFocused(isFocused: Boolean) {
        _isInsightTitleFocused.value = isFocused
    }

    fun updateInsightTitleValid(isValid: Boolean) {
        _isInsightTitleValid.value = isValid
    }

    fun updateInsightAptAddress(address: String) {
        _insightAptAddress.value = address
    }

    fun updateInsightAptName(name: String) {
        _insightAptName.value = name
    }

    fun updateAptAddressValid(isValid: Boolean) {
        _isInsightAptAddressValid.value = isValid
    }

    fun updateInsightVisitDate(visitDate: String) {
        _insightVisitDate.value = visitDate
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
        updateProgress()
    }

    fun updateComplexEnvironmentReview(complexEnvironmentReview: String) {
        _complexEnvironmentReview.value = complexEnvironmentReview
        updateProgress()
    }

    fun updateComplexFacilityReview(complexFacility: String) {
        _complexFacilityReview.value = complexFacility
        updateProgress()
    }

    fun updateGoodNewsReview(goodNews: String) {
        _goodNewsReview.value = goodNews
        updateProgress()
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

    fun writeInsight() {
        viewModelScope.launch {
            val writeInsightDto = WriteInsightDto(
                score = progress.value.replace("%", "").toInt(),
                address = AddressDto(
                    siGunGu = insightAptAddress.value,
                    dong = insightAptAddress.value
                ),
                apartmentComplex = ApartmentComplexDto(
                    name = "${insightAptAddress.value[0]} ${insightAptAddress.value[1]}",
                    key = "${insightAptAddress.value[2]} ${insightAptAddress.value[3]}"
                ),
                title = insightTitle.value,
                contents = "",
                mainImage = "",
                summary = insightSummary.value,
                visitAt = insightVisitDate.value,
                visitMethod = insightSelectedTraffics.value.first(), // todo : insightSelectedTraffics.value
                access = insightSelectedEntrances.value ?: return@launch,
                infra = InfraDto(
                    transportation = MultiChoiceDto(
                        choice = infraTrafficManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    schoolDistrict = MultiChoiceDto(
                        choice = infraSchoolManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    amenity = MultiChoiceDto(
                        choice = infraLivingAmenityManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    facility = MultiChoiceDto(
                        choice = infraFacilitiesManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    surroundings = MultiChoiceDto(
                        choice = infraEnvironmentManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    landmark = MultiChoiceDto(
                        choice = infraLandmarkManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    unpleasantFacility = MultiChoiceDto(
                        choice = infraAvoidFacilityManager.selectedItems.value.toList(),
                        text = ""
                    )
                ),
                complexEnvironment = ComplexEnvironmentDto(
                    buildingCondition = SingleChoiceDto(
                        choice = complexEnvironmentBuildingManager.selectedItems.value.firstOrNull() ?: "",
                        text = ""
                    ),
                    security = SingleChoiceDto(
                        choice = complexEnvironmentSafetyManager.selectedItems.value.firstOrNull() ?: "",
                        text = ""
                    ),
                    childrenFacility = SingleChoiceDto(
                        choice = complexEnvironmentChildrenFacilityManager.selectedItems.value.firstOrNull() ?: "",
                        text = ""
                    ),
                    seniorFacility = SingleChoiceDto(
                        choice = complexEnvironmentSilverFacilityManager.selectedItems.value.firstOrNull() ?: "",
                        text = ""
                    )
                ),
                complexFacility = ComplexFacilityDto(
                    family = MultiChoiceDto(
                        choice = complexFacilityFamilyManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    multipurpose = MultiChoiceDto(
                        choice = complexFacilityMultiPurposeManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    leisure = MultiChoiceDto(
                        choice = complexFacilityLeisureManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    surroundings = MultiChoiceDto(
                        choice = complexFacilityEnvironmentManager.selectedItems.value.toList(),
                        text = ""
                    )
                ),
                favorableNews = FavorableNewsDto(
                    transportation = MultiChoiceDto(
                        choice = goodNewsTrafficManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    development = MultiChoiceDto(
                        choice = goodNewsDevelopmentManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    education = MultiChoiceDto(
                        choice = goodNewsEducationManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    environment = MultiChoiceDto(
                        choice = goodNewsNaturalEnvironmentManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    culture = MultiChoiceDto(
                        choice = goodNewsCultureManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    industry = MultiChoiceDto(
                        choice = goodNewsIndustryManager.selectedItems.value.toList(),
                        text = ""
                    ),
                    policy = MultiChoiceDto(
                        choice = goodNewsPolicyManager.selectedItems.value.toList(),
                        text = ""
                    )
                )
            )

            Log.d("##", "$writeInsightDto")

            writeInsightUseCase(writeInsightDto)
        }
    }
}
