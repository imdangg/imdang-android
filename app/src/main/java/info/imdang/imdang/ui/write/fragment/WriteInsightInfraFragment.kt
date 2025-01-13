package info.imdang.imdang.ui.write.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.util.SelectionManager
import info.imdang.imdang.common.util.SelectionUtils.setupSelectionHandlers
import info.imdang.imdang.common.util.SelectionUtils.updateMultiSelectionUI
import info.imdang.imdang.databinding.FragmentWriteInsightInfraBinding
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.write.WriteInsightViewModel
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity.Companion.OVERALL_REVIEW
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity.Companion.OVERALL_REVIEW_TITLE
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WriteInsightInfraFragment :
    BaseFragment<FragmentWriteInsightInfraBinding>(R.layout.fragment_write_insight_infra) {

    private val viewModel by activityViewModels<WriteInsightViewModel>()

    private val overallReviewResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra(OVERALL_REVIEW)?.let {
                viewModel.updateInfraReview(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setupListener()
        observe()
    }

    private fun init() {
        with(binding) {
            viewModel = this@WriteInsightInfraFragment.viewModel
        }
    }

    private fun observe() {
        // 교통
        val traffics = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to binding.tvTrafficNot,
            getString(info.imdang.component.R.string.convenient_parking) to
                binding.tvTrafficConvenient,
            getString(info.imdang.component.R.string.around_subway_station) to
                binding.tvTrafficSubway,
            getString(info.imdang.component.R.string.around_the_bus_stop) to binding.tvTrafficBus
        )

        traffics.forEach { (traffic, textView) ->
            textView.setOnClickListener {
                viewModel.infraTrafficManager.toggleSelection(traffic)
            }
        }

        // 학군
        val schools = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvSchoolDistrictNot,
            getString(info.imdang.component.R.string.apartment_with_elementary_school) to
                binding.tvSchoolDistrictApartmentWithElementarySchool,
            getString(info.imdang.component.R.string.daycare_center) to
                binding.tvSchoolDistrictDaycareCenter,
            getString(info.imdang.component.R.string.middle_school) to
                binding.tvSchoolDistrictMiddleSchool,
            getString(info.imdang.component.R.string.high_school) to
                binding.tvSchoolDistrictHighSchool,
            getString(info.imdang.component.R.string.academy_district) to
                binding.tvSchoolDistrictAcademyDistrict
        )

        schools.forEach { (school, textView) ->
            textView.setOnClickListener {
                viewModel.infraSchoolManager.toggleSelection(school)
            }
        }

        // 생활 편의시설
        val livingAmenities = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvLivingAmenitiesNot,
            getString(info.imdang.component.R.string.community_center) to
                binding.tvLivingAmenitiesCommunityCenter,
            getString(info.imdang.component.R.string.convenience_store) to
                binding.tvLivingAmenitiesConvenienceStore,
            getString(info.imdang.component.R.string.small_mart) to
                binding.tvLivingAmenitiesSmallMart,
            getString(info.imdang.component.R.string.large_mart) to
                binding.tvLivingAmenitiesLargeMart,
            getString(info.imdang.component.R.string.hospital) to
                binding.tvLivingAmenitiesHospital,
            getString(info.imdang.component.R.string.bank) to
                binding.tvLivingAmenitiesBank,
            getString(info.imdang.component.R.string.cafe) to
                binding.tvLivingAmenitiesCafe,
            getString(info.imdang.component.R.string.beauty_salon) to
                binding.tvLivingAmenitiesBeautySalon,
            getString(info.imdang.component.R.string.pharmacy) to
                binding.tvLivingAmenitiesPharmacy,
            getString(info.imdang.component.R.string.post_office) to
                binding.tvLivingAmenitiesPostOffice
        )

        livingAmenities.forEach { (livingAmenity, textView) ->
            textView.setOnClickListener {
                viewModel.infraLivingAmenityManager.toggleSelection(livingAmenity)
            }
        }

        // 문화 및 여가시설 (단지외부)
        val facilities = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvFacilitiesNot,
            getString(info.imdang.component.R.string.library) to
                binding.tvFacilitiesLibrary,
            getString(info.imdang.component.R.string.cinema) to
                binding.tvFacilitiesCinema,
            getString(info.imdang.component.R.string.gym) to
                binding.tvFacilitiesGym,
            getString(info.imdang.component.R.string.fitness_center) to
                binding.tvFacilitiesFitnessCenter,
            getString(info.imdang.component.R.string.swimming_pool) to
                binding.tvFacilitiesSwimmingPool,
            getString(info.imdang.component.R.string.badminton_court) to
                binding.tvFacilitiesBadmintonCourt,
            getString(info.imdang.component.R.string.tennis_court) to
                binding.tvFacilitiesTennisCourt,
            getString(info.imdang.component.R.string.golf_driving_range) to
                binding.tvFacilitiesGolfDrivingRange
        )

        facilities.forEach { (facility, textView) ->
            textView.setOnClickListener {
                viewModel.infraFacilitiesManager.toggleSelection(facility)
            }
        }

        // 주변 환경
        val environments = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvEnvironmentNot,
            getString(info.imdang.component.R.string.river) to
                binding.tvEnvironmentRiver,
            getString(info.imdang.component.R.string.ocean) to
                binding.tvEnvironmentOcean,
            getString(info.imdang.component.R.string.mountain) to
                binding.tvEnvironmentMountain,
            getString(info.imdang.component.R.string.park) to
                binding.tvEnvironmentPark,
            getString(info.imdang.component.R.string.walking_trail) to
                binding.tvEnvironmentWalkingTrail,
            getString(info.imdang.component.R.string.church) to
                binding.tvEnvironmentChurch,
            getString(info.imdang.component.R.string.cathedral) to
                binding.tvEnvironmentCathedral,
            getString(info.imdang.component.R.string.restaurant_area) to
                binding.tvEnvironmentRestaurantArea,
            getString(info.imdang.component.R.string.market) to
                binding.tvEnvironmentMarket
        )

        environments.forEach { (environment, textView) ->
            textView.setOnClickListener {
                viewModel.infraEnvironmentManager.toggleSelection(environment)
            }
        }

        // 랜드 마크
        val landmarks = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvLandmarkNot,
            getString(info.imdang.component.R.string.amusement_park) to
                binding.tvLandmarkAmusementPark,
            getString(info.imdang.component.R.string.complex_shopping_mall) to
                binding.tvLandmarkComplexShoppingMall,
            getString(info.imdang.component.R.string.old_palace) to
                binding.tvLandmarkOldPalace,
            getString(info.imdang.component.R.string.observatory) to
                binding.tvLandmarkObservatory,
            getString(info.imdang.component.R.string.national_park) to
                binding.tvLandmarkNationalPark,
            getString(info.imdang.component.R.string.hanok_village) to
                binding.tvLandmarkHanokVillage,
            getString(info.imdang.component.R.string.temple) to
                binding.tvLandmarkTemple,
            getString(info.imdang.component.R.string.art_gallery) to
                binding.tvLandmarkArtGallery,
            getString(info.imdang.component.R.string.museum) to
                binding.tvLandmarkMuseum
        )

        landmarks.forEach { (landmark, textView) ->
            textView.setOnClickListener {
                viewModel.infraLandmarkManager.toggleSelection(landmark)
            }
        }

        // 기피 시설
        val avoidFacilities = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvAvoidFacilityNot,
            getString(info.imdang.component.R.string.highway) to
                binding.tvAvoidFacilityHighway,
            getString(info.imdang.component.R.string.railway) to
                binding.tvAvoidFacilityRailway,
            getString(info.imdang.component.R.string.entertainment_street) to
                binding.tvAvoidFacilityEntertainmentStreet,
            getString(info.imdang.component.R.string.industrial_complex) to
                binding.tvAvoidFacilityIndustrialComplex,
            getString(info.imdang.component.R.string.factory) to
                binding.tvAvoidFacilityFactory,
            getString(info.imdang.component.R.string.garbage_incinerator) to
                binding.tvAvoidFacilityGarbageIncinerator,
            getString(info.imdang.component.R.string.high_rise_building) to
                binding.tvAvoidFacilityHighRiseBuilding,
            getString(info.imdang.component.R.string.building_under_construction) to
                binding.tvAvoidFacilityBuildingUnderConstruction
        )

        avoidFacilities.forEach { (avoidFacility, textView) ->
            textView.setOnClickListener {
                viewModel.infraLandmarkManager.toggleSelection(avoidFacility)
            }
        }

        setupSelectionHandlers(traffics, viewModel.infraTrafficManager::toggleSelection)
        setupSelectionHandlers(schools, viewModel.infraSchoolManager::toggleSelection)
        setupSelectionHandlers(
            livingAmenities,
            viewModel.infraLivingAmenityManager::toggleSelection
        )
        setupSelectionHandlers(facilities, viewModel.infraFacilitiesManager::toggleSelection)
        setupSelectionHandlers(environments, viewModel.infraEnvironmentManager::toggleSelection)
        setupSelectionHandlers(landmarks, viewModel.infraLandmarkManager::toggleSelection)
        setupSelectionHandlers(
            avoidFacilities,
            viewModel.infraAvoidFacilityManager::toggleSelection
        )

        lifecycleScope.launch {
            // 교통
            launch {
                viewModel.infraTrafficManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = traffics,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.infraTrafficManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.infraTrafficManager)
                    }
                }
            }

            // 학군
            launch {
                viewModel.infraSchoolManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = schools,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.infraSchoolManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.infraSchoolManager)
                    }
                }
            }

            // 생활 편의시설
            launch {
                viewModel.infraLivingAmenityManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = livingAmenities,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.infraLivingAmenityManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.infraLivingAmenityManager)
                    }
                }
            }

            // 문화 및 여가시설 (단지외부)
            launch {
                viewModel.infraFacilitiesManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = facilities,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.infraFacilitiesManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.infraFacilitiesManager)
                    }
                }
            }

            // 주변 환경
            launch {
                viewModel.infraEnvironmentManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = environments,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.infraEnvironmentManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.infraEnvironmentManager)
                    }
                }
            }

            // 랜드마크
            launch {
                viewModel.infraLandmarkManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = landmarks,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.infraLandmarkManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.infraLandmarkManager)
                    }
                }
            }

            // 기피 시설
            launch {
                viewModel.infraAvoidFacilityManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = avoidFacilities,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.infraAvoidFacilityManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.infraAvoidFacilityManager)
                    }
                }
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            viewInfraOverallReview.setOnClickListener {
                openWriteOverallReviewActivity()
            }
            tvInfraOverallReview.setOnClickListener {
                openWriteOverallReviewActivity()
            }
        }
    }

    private fun openWriteOverallReviewActivity() {
        overallReviewResult.launch(
            Intent(requireContext(), WriteOverallReviewActivity::class.java).apply {
                putExtra(
                    OVERALL_REVIEW_TITLE,
                    getString(info.imdang.component.R.string.infra_overall_review)
                )
                putExtra(
                    OVERALL_REVIEW,
                    this@WriteInsightInfraFragment.viewModel.infraReview.value
                )
            }
        )
    }

    private fun resetSelectionDialog(manager: SelectionManager) {
        requireContext().showCommonDialog(
            message = getString(info.imdang.component.R.string.write_insight_unselect_message),
            positiveButtonText = getString(info.imdang.component.R.string.yes_its_ok),
            negativeButtonText = getString(info.imdang.component.R.string.cancel),
            onClickPositiveButton = { manager.confirmReset() },
            onClickNegativeButton = { manager.cancelReset() }
        )
    }

    companion object {
        fun instance(): WriteInsightInfraFragment = WriteInsightInfraFragment()
    }
}
