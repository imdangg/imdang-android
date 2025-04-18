package info.imdang.imdang.ui.write.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.util.SelectionManager
import info.imdang.imdang.common.util.SelectionUtils.updateMultiSelectionUI
import info.imdang.imdang.databinding.FragmentWriteInsightComplexFacilityBinding
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.write.WriteInsightViewModel
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity.Companion.OVERALL_REVIEW
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity.Companion.OVERALL_REVIEW_TITLE
import kotlinx.coroutines.launch

class WriteInsightComplexFacilityFragment :
    BaseFragment<FragmentWriteInsightComplexFacilityBinding>(
        R.layout.fragment_write_insight_complex_facility
    ) {

    private val viewModel by activityViewModels<WriteInsightViewModel>()

    private val overallReviewResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra(OVERALL_REVIEW)?.let {
                viewModel.updateComplexFacilityReview(it)
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
            viewModel = this@WriteInsightComplexFacilityFragment.viewModel
        }
    }

    private fun observe() {
        // 가족
        val families = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvFamilyNotApplicable,
            getString(info.imdang.component.R.string.daycare_center) to
                binding.tvFamilyDaycareCenter,
            getString(info.imdang.component.R.string.senior_center) to
                binding.tvFamilySeniorCenter
        )

        families.forEach { (family, textView) ->
            textView.setOnClickListener {
                viewModel.complexFacilityFamilyManager.toggleSelection(family)
            }
        }

        // 다목적
        val multiPurposes = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvMultipurposeNotApplicable,
            getString(info.imdang.component.R.string.multipurpose_room) to
                binding.tvMultipurposeRoom,
            getString(info.imdang.component.R.string.resident_representative_conference_room)
                to binding.tvMultipurposeConferenceRoom,
            getString(info.imdang.component.R.string.public_laundry) to
                binding.tvMultipurposePublicLaundry,
            getString(info.imdang.component.R.string.public_lounge) to
                binding.tvMultipurposePublicLounge
        )

        multiPurposes.forEach { (multiPurpose, textView) ->
            textView.setOnClickListener {
                viewModel.complexFacilityMultiPurposeManager.toggleSelection(multiPurpose)
            }
        }

        // 여가 (단지내부)
        val leisures = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvLeisureNotApplicable,
            getString(info.imdang.component.R.string.leisure_fitness_center) to
                binding.tvLeisureFitnessCenter,
            getString(info.imdang.component.R.string.reading_room) to
                binding.tvLeisureReadingRoom,
            getString(info.imdang.component.R.string.sauna) to
                binding.tvLeisureSauna,
            getString(info.imdang.component.R.string.baths) to
                binding.tvLeisureBaths,
            getString(info.imdang.component.R.string.screen_golf_course) to
                binding.tvLeisureScreenGolfCourse,
            getString(info.imdang.component.R.string.cinema) to
                binding.tvLeisureCinema,
            getString(info.imdang.component.R.string.library) to
                binding.tvLeisureLibrary,
            getString(info.imdang.component.R.string.swimming_pool) to
                binding.tvLeisureSwimmingPool,
            getString(info.imdang.component.R.string.study_room) to
                binding.tvLeisureStudyRoom,
            getString(info.imdang.component.R.string.daycare_center) to
                binding.tvLeisureDaycareCenter,
            getString(info.imdang.component.R.string.guest_house) to
                binding.tvLeisureGuestHouse,
            getString(info.imdang.component.R.string.water_park) to
                binding.tvLeisureWaterPark,
            getString(info.imdang.component.R.string.breakfast) to
                binding.tvLeisureBreakfast
        )

        leisures.forEach { (leisure, textView) ->
            textView.setOnClickListener {
                viewModel.complexFacilityLeisureManager.toggleSelection(leisure)
            }
        }

        // 환경
        val environments = mapOf(
            getString(info.imdang.component.R.string.not_applicable) to
                binding.tvEnvironmentNotApplicable,
            getString(info.imdang.component.R.string.lawn) to
                binding.tvEnvironmentLawn,
            getString(info.imdang.component.R.string.sculpture) to
                binding.tvEnvironmentSculpture,
            getString(info.imdang.component.R.string.bench) to
                binding.tvEnvironmentBench,
            getString(info.imdang.component.R.string.tables_and_chairs) to
                binding.tvEnvironmentTablesAndChairs,
            getString(info.imdang.component.R.string.fountain) to
                binding.tvEnvironmentFountain
        )

        environments.forEach { (environment, textView) ->
            textView.setOnClickListener {
                viewModel.complexFacilityEnvironmentManager.toggleSelection(environment)
            }
        }

        lifecycleScope.launch {
            // 가족
            launch {
                viewModel.complexFacilityFamilyManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = families,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.complexFacilityFamilyManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.complexFacilityFamilyManager)
                    }
                }
            }

            // 다목적
            launch {
                viewModel
                    .complexFacilityMultiPurposeManager
                    .selectedItems
                    .collect { selectedItems ->
                        updateMultiSelectionUI(
                            items = multiPurposes,
                            selectedItems = selectedItems,
                            context = requireContext()
                        )
                    }
            }

            launch {
                viewModel.complexFacilityMultiPurposeManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.complexFacilityMultiPurposeManager)
                    }
                }
            }

            // 여가 (단지내부)
            launch {
                viewModel.complexFacilityLeisureManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = leisures,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.complexFacilityLeisureManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.complexFacilityLeisureManager)
                    }
                }
            }

            // 환경
            launch {
                viewModel.complexFacilityEnvironmentManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = environments,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.complexFacilityEnvironmentManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.complexFacilityEnvironmentManager)
                    }
                }
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            viewAptFacilityOverallReview.setOnClickListener {
                openWriteOverallReviewActivity()
            }
            tvAptFacilityReview.setOnClickListener {
                openWriteOverallReviewActivity()
            }
        }
    }

    private fun openWriteOverallReviewActivity() {
        overallReviewResult.launch(
            Intent(requireContext(), WriteOverallReviewActivity::class.java).apply {
                putExtra(
                    OVERALL_REVIEW_TITLE,
                    getString(info.imdang.component.R.string.apt_facility_review)
                )
                putExtra(
                    OVERALL_REVIEW,
                    this@WriteInsightComplexFacilityFragment
                        .viewModel.complexFacilityReview.value
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
        fun instance(): WriteInsightComplexFacilityFragment = WriteInsightComplexFacilityFragment()
    }
}
