package info.imdang.imdang.ui.write

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.insight.ApartmentComplexDto
import info.imdang.domain.model.insight.ComplexEnvironmentDto
import info.imdang.domain.model.insight.ComplexFacilityDto
import info.imdang.domain.model.insight.FavorableNewsDto
import info.imdang.domain.model.insight.InfraDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.usecase.insight.GetInsightDetailUseCase
import info.imdang.domain.usecase.insight.UpdateInsightParams
import info.imdang.domain.usecase.insight.UpdateInsightUseCase
import info.imdang.domain.usecase.insight.WriteInsightParams
import info.imdang.domain.usecase.insight.WriteInsightUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.common.util.SelectionManager
import info.imdang.imdang.common.util.formatDate
import info.imdang.imdang.common.util.formatSelectedItems
import info.imdang.imdang.model.insight.mapper
import info.imdang.imdang.ui.write.WriteInsightActivity.Companion.INSIGHT_ID
import java.io.File
import java.util.Locale
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteInsightViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getInsightDetailUseCase: GetInsightDetailUseCase,
    private val writeInsightUseCase: WriteInsightUseCase,
    private val updateInsightUseCase: UpdateInsightUseCase
) : BaseViewModel() {

    private val insightId: String? = savedStateHandle[INSIGHT_ID]

    private val _event = MutableSharedFlow<WriteInsightEvent>()
    val event = _event.asSharedFlow()

    private val _progress = MutableStateFlow("00%")
    val progress = _progress.asStateFlow()

    private val _isPreviousButtonVisible = MutableStateFlow(false)
    val isPreviousButtonVisible = _isPreviousButtonVisible.asStateFlow()

    private val _selectedPage = MutableStateFlow(0)
    val selectedPage = _selectedPage.asStateFlow()

    private val _isTooltipVisible = MutableStateFlow(false)
    val isTooltipVisible = _isTooltipVisible.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled = _isButtonEnabled.asStateFlow()

    // 기본 정보
    private val _coverImageFile = MutableStateFlow<File?>(null)
    val coverImageFile = _coverImageFile.asStateFlow()

    private val _coverImageUrl = MutableStateFlow<String?>(null)
    val coverImageUrl = _coverImageUrl.asStateFlow()

    private val _isCoverImageValid = MutableStateFlow(false)
    val isCoverImageValid = _isCoverImageValid.asStateFlow()

    private val _insightTitle = MutableStateFlow("")
    val insightTitle = _insightTitle.asStateFlow()

    private val _isInsightTitleFocused = MutableStateFlow(false)
    val isInsightTitleFocused = _isInsightTitleFocused.asStateFlow()

    private val _isInsightTitleValid = MutableStateFlow(false)
    val isInsightTitleValid = _isInsightTitleValid.asStateFlow()

    private val _insightAptAddress = MutableStateFlow("")
    val insightAptAddress = _insightAptAddress.asStateFlow()

    private val _insightAptName = MutableStateFlow("")
    val insightAptName = _insightAptName.asStateFlow()

    private val _latitude = MutableStateFlow(0.0)
    private val latitude = _latitude.asStateFlow()

    private val _longitude = MutableStateFlow(0.0)
    private val longitude = _longitude.asStateFlow()

    private val _isInsightAptAddressValid = MutableStateFlow(false)
    val isInsightAptAddressValid = _isInsightAptAddressValid.asStateFlow()

    private val _insightVisitDate = MutableStateFlow("")
    val insightVisitDate = _insightVisitDate.asStateFlow()

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
        isCoverImageValid,
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

    init {
        fetchInsightDetail()
    }

    private fun fetchInsightDetail() {
        insightId ?: return
        viewModelScope.launch {
            val insight = getInsightDetailUseCase(insightId)?.mapper() ?: return@launch

            // 기본 정보
            _coverImageUrl.value = insight.mainImage
            _isCoverImageValid.value = true
            _insightTitle.value = insight.title
            _isInsightTitleValid.value = true
            _insightAptAddress.value = insight.address.toJibunAddress()
            _isInsightAptAddressValid.value = true
            _insightAptName.value = insight.aptComplex
            _latitude.value = insight.address.latitude ?: 0.0
            _longitude.value = insight.address.longitude ?: 0.0
            _insightVisitDate.value = insight.visitAt
            _isInsightDateValid.value = true
            _insightSelectedTimes.value = insight.visitTimes.toSet()
            _insightSelectedTraffics.value = insight.visitMethods.toSet()
            _insightSelectedEntrances.value = insight.access
            _insightSummary.value = insight.summary
            // 인프라
            insight.infra?.run {
                infraTrafficManager.updateSelectedItems(traffics.toSet())
                infraSchoolManager.updateSelectedItems(schools.toSet())
                infraLivingAmenityManager.updateSelectedItems(lifeFacilities.toSet())
                infraFacilitiesManager.updateSelectedItems(cultureFacilities.toSet())
                infraEnvironmentManager.updateSelectedItems(surroundingEnvironments.toSet())
                infraLandmarkManager.updateSelectedItems(landmarks.toSet())
                infraAvoidFacilityManager.updateSelectedItems(avoidFacilities.toSet())
                _infraReview.value = infraReview
            }
            // 단지 환경
            insight.complexEnvironment?.run {
                complexEnvironmentBuildingManager.updateSelectedItem(building)
                complexEnvironmentSafetyManager.updateSelectedItem(safety)
                complexEnvironmentChildrenFacilityManager.updateSelectedItem(childrenFacility)
                complexEnvironmentSilverFacilityManager.updateSelectedItem(silverFacility)
                _complexFacilityReview.value = complexEnvironmentReview
            }
            // 단지 시설
            insight.complexFacility?.run {
                complexFacilityFamilyManager.updateSelectedItems(familyFacilities.toSet())
                complexFacilityMultiPurposeManager
                    .updateSelectedItems(multipurposeFacilities.toSet())
                complexFacilityLeisureManager.updateSelectedItems(leisureFacilities.toSet())
                complexFacilityEnvironmentManager.updateSelectedItems(environments.toSet())
                _complexFacilityReview.value = complexFacilityReview
            }
            // 호재
            insight.goodNews?.run {
                goodNewsTrafficManager.updateSelectedItems(traffics.toSet())
                goodNewsDevelopmentManager.updateSelectedItems(developments.toSet())
                goodNewsEducationManager.updateSelectedItems(educations.toSet())
                goodNewsNaturalEnvironmentManager.updateSelectedItems(naturalEnvironments.toSet())
                goodNewsCultureManager.updateSelectedItems(cultures.toSet())
                goodNewsIndustryManager.updateSelectedItems(industries.toSet())
                goodNewsPolicyManager.updateSelectedItems(policies.toSet())
                _goodNewsReview.value = goodNewsReview
            }

            _event.emit(WriteInsightEvent.UpdateButtonState)
        }
    }

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

    fun updateButtonEnabled(isEnabled: Boolean) {
        _isButtonEnabled.value = isEnabled
    }

    fun updateCoverImageFile(file: File?) {
        _coverImageFile.value = file
        _isCoverImageValid.value = true
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

    fun updateLatitude(latitude: Double) {
        _latitude.value = latitude
    }

    fun updateLongitude(longitude: Double) {
        _longitude.value = longitude
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

    fun writeInsight() {
        viewModelScope.launch {
            _isButtonEnabled.value = false
            val writeInsightDto = WriteInsightDto(
                insightId = insightId,
                score = progress.value.replace("%", "").toInt(),
                title = insightTitle.value,
                address = AddressDto(
                    siDo = insightAptAddress.value.split(" ")[0],
                    siGunGu = insightAptAddress.value.split(" ")[1],
                    eupMyeonDong = insightAptAddress.value.split(" ")[2],
                    roadName = "",
                    buildingNumber = insightAptAddress.value.split(" ")[3],
                    detail = "",
                    latitude = latitude.value,
                    longitude = longitude.value
                ),
                apartmentComplex = ApartmentComplexDto(
                    name = insightAptName.value
                ),
                visitAt = insightVisitDate.value.formatDate(
                    fromFormat = "yyyy.MM.dd",
                    toFormat = "yyyy-MM-dd"
                ),
                visitTimes = insightSelectedTimes.formatSelectedItems(),
                visitMethods = insightSelectedTraffics.formatSelectedItems(),
                access = insightSelectedEntrances.value?.replace(" ", "_") ?: "",
                summary = insightSummary.value,
                infra = InfraDto(
                    transportations = infraTrafficManager.formattedSelectedItems(),
                    schoolDistricts = infraSchoolManager.formattedSelectedItems(),
                    amenities = infraLivingAmenityManager.formattedSelectedItems(),
                    facilities = infraFacilitiesManager.formattedSelectedItems(),
                    surroundings = infraEnvironmentManager.formattedSelectedItems(),
                    landmarks = infraLandmarkManager.formattedSelectedItems(),
                    unpleasantFacilities = infraAvoidFacilityManager.formattedSelectedItems(),
                    text = infraReview.value
                ),
                complexEnvironment = ComplexEnvironmentDto(
                    buildingCondition = complexEnvironmentBuildingManager.selectedSingleItem(),
                    security = complexEnvironmentSafetyManager.selectedSingleItem(),
                    childrenFacility = complexEnvironmentChildrenFacilityManager
                        .selectedSingleItem(),
                    seniorFacility = complexEnvironmentSilverFacilityManager.selectedSingleItem(),
                    text = complexEnvironmentReview.value
                ),
                complexFacility = ComplexFacilityDto(
                    familyFacilities = complexFacilityFamilyManager.formattedSelectedItems(),
                    multipurposeFacilities = complexFacilityMultiPurposeManager
                        .formattedSelectedItems(),
                    leisureFacilities = complexFacilityLeisureManager.formattedSelectedItems(),
                    surroundings = complexFacilityEnvironmentManager.formattedSelectedItems(),
                    text = complexFacilityReview.value
                ),
                favorableNews = FavorableNewsDto(
                    transportations = goodNewsTrafficManager.formattedSelectedItems(),
                    developments = goodNewsDevelopmentManager.formattedSelectedItems(),
                    educations = goodNewsEducationManager.formattedSelectedItems(),
                    environments = goodNewsNaturalEnvironmentManager.formattedSelectedItems(),
                    cultures = goodNewsCultureManager.formattedSelectedItems(),
                    industries = goodNewsIndustryManager.formattedSelectedItems(),
                    policies = goodNewsPolicyManager.formattedSelectedItems(),
                    text = goodNewsReview.value
                )
            )

            if (insightId == null) {
                // 인사이트 작성
                writeInsightUseCase(
                    WriteInsightParams(
                        writeInsightDto = writeInsightDto,
                        mainImage = coverImageFile.value ?: return@launch
                    ),
                    onError = {
                        _isButtonEnabled.value = true
                        it.message?.let {
                            launch {
                                _event.emit(WriteInsightEvent.ShowToast(it))
                            }
                        }
                    }
                )?.let {
                    _event.emit(WriteInsightEvent.WriteInsightComplete(it.insightId))
                }
            } else {
                // 인사이트 수정
                updateInsightUseCase(
                    UpdateInsightParams(
                        writeInsightDto = writeInsightDto,
                        mainImage = coverImageFile.value
                    ),
                    onError = {
                        _isButtonEnabled.value = true
                        it.message?.let {
                            launch {
                                _event.emit(WriteInsightEvent.ShowToast(it))
                            }
                        }
                    }
                )?.let {
                    _event.emit(WriteInsightEvent.WriteInsightComplete(it.insightId))
                }
            }
        }
    }
}
