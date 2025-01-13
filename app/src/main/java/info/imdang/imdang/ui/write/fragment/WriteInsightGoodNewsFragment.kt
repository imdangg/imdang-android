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
import info.imdang.imdang.databinding.FragmentWriteInsightGoodNewsBinding
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.write.WriteInsightViewModel
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity.Companion.OVERALL_REVIEW
import info.imdang.imdang.ui.write.review.WriteOverallReviewActivity.Companion.OVERALL_REVIEW_TITLE
import kotlinx.coroutines.launch

class WriteInsightGoodNewsFragment : BaseFragment<FragmentWriteInsightGoodNewsBinding>(
    R.layout.fragment_write_insight_good_news
) {

    private val viewModel by activityViewModels<WriteInsightViewModel>()

    private val overallReviewResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra(OVERALL_REVIEW)?.let {
                viewModel.updateTooltipVisible(false)
                viewModel.updateGoodNewsReview(it)
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
            viewModel = this@WriteInsightGoodNewsFragment.viewModel
        }
    }

    private fun observe() {
        // 교통
        val traffics = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvTrafficNot,
            getString(info.imdang.component.R.string.subway_opens) to
                binding.tvTrafficSubway,
            getString(info.imdang.component.R.string.new_high_speed_rail_station_established) to
                binding.tvTrafficHighSpeedRailStation,
            getString(info.imdang.component.R.string.transportation_hub_designation) to
                binding.tvTrafficTransportationHub
        )

        traffics.forEach { (traffic, textView) ->
            textView.setOnClickListener {
                viewModel.updateTooltipVisible(false)
                viewModel.goodNewsTrafficManager.toggleSelection(traffic, "잘 모르겠어요")
            }
        }

        // 개발
        val developments = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvDevelopmentNot,
            getString(info.imdang.component.R.string.redevelopment) to
                binding.tvDevelopmentRedevelopment,
            getString(info.imdang.component.R.string.reconstruction) to
                binding.tvDevelopmentReconstruction,
            getString(info.imdang.component.R.string.remodeling) to
                binding.tvDevelopmentRemodeling,
            getString(info.imdang.component.R.string.nearby_new_town_development) to
                binding.tvDevelopmentNearbyNewTown,
            getString(info.imdang.component.R.string.complex_development) to
                binding.tvDevelopmentComplex,
            getString(info.imdang.component.R.string.large_shopping_mall) to
                binding.tvDevelopmentLargeShoppingMall,
            getString(info.imdang.component.R.string.department_store) to
                binding.tvDevelopmentDepartmentStore,
            getString(info.imdang.component.R.string.large_office_complex) to
                binding.tvDevelopmentLargeOfficeComplex
        )

        developments.forEach { (development, textView) ->
            textView.setOnClickListener {
                viewModel.updateTooltipVisible(false)
                viewModel.goodNewsDevelopmentManager.toggleSelection(development, "잘 모르겠어요")
            }
        }

        // 교육
        val educations = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvEducationNot,
            getString(info.imdang.component.R.string.new_elementary_school) to
                binding.tvEducationNewElementarySchool,
            getString(info.imdang.component.R.string.new_high_school) to
                binding.tvEducationNewHighSchool,
            getString(info.imdang.component.R.string.special_high_school) to
                binding.tvEducationSpecialHighSchool,
            getString(info.imdang.component.R.string.private_high_school) to
                binding.tvEducationPrivateHighSchool,
            getString(info.imdang.component.R.string.international_school) to
                binding.tvEducationInternationalSchool,
            getString(info.imdang.component.R.string.college_campus) to
                binding.tvEducationCollegeCampus
        )

        educations.forEach { (education, textView) ->
            textView.setOnClickListener {
                viewModel.updateTooltipVisible(false)
                viewModel.goodNewsEducationManager.toggleSelection(education, "잘 모르겠어요")
            }
        }

        // 자연환경
        val naturalEnvironments = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvNaturalEnvironmentNot,
            getString(info.imdang.component.R.string.large_park) to
                binding.tvNaturalEnvironmentLargePark,
            getString(info.imdang.component.R.string.river_restoration) to
                binding.tvNaturalEnvironmentRiverRestoration,
            getString(info.imdang.component.R.string.lake_restoration) to
                binding.tvNaturalEnvironmentLakeRestoration
        )

        naturalEnvironments.forEach { (naturalEnvironment, textView) ->
            textView.setOnClickListener {
                viewModel.updateTooltipVisible(false)
                viewModel.goodNewsNaturalEnvironmentManager
                    .toggleSelection(naturalEnvironment, "잘 모르겠어요")
            }
        }

        // 문화
        val cultures = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvCultureNot,
            getString(info.imdang.component.R.string.large_hospital) to
                binding.tvCultureLargeHospital,
            getString(info.imdang.component.R.string.cultural_center) to
                binding.tvCultureCulturalCenter,
            getString(info.imdang.component.R.string.library) to
                binding.tvCultureLibrary,
            getString(info.imdang.component.R.string.performance_hall) to
                binding.tvCulturePerformanceHall,
            getString(info.imdang.component.R.string.gym) to
                binding.tvCultureGym
        )

        cultures.forEach { (culture, textView) ->
            textView.setOnClickListener {
                viewModel.updateTooltipVisible(false)
                viewModel.goodNewsCultureManager.toggleSelection(culture, "잘 모르겠어요")
            }
        }

        // 산업
        val industries = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvIndustryNot,
            getString(info.imdang.component.R.string.industry_complex) to
                binding.tvIndustryComplex,
            getString(info.imdang.component.R.string.corporate_relocation) to
                binding.tvIndustryCorporateRelocation,
            getString(info.imdang.component.R.string.startup_complex) to
                binding.tvIndustryStartupComplex
        )

        industries.forEach { (industry, textView) ->
            textView.setOnClickListener {
                viewModel.updateTooltipVisible(false)
                viewModel.goodNewsIndustryManager.toggleSelection(industry, "잘 모르겠어요")
            }
        }

        // 정책
        val policies = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvPolicyNot,
            getString(info.imdang.component.R.string.speculative_overheating_district_unlocked) to
                binding.tvPolicyUnlocked,
            getString(info.imdang.component.R.string.deregulation) to
                binding.tvPolicyDeregulation,
            getString(info.imdang.component.R.string.special_zone_designation) to
                binding.tvPolicySpecialZone,
            getString(info.imdang.component.R.string.job_creation_policy) to
                binding.tvPolicyJob
        )

        policies.forEach { (policy, textView) ->
            textView.setOnClickListener {
                viewModel.updateTooltipVisible(false)
                viewModel.goodNewsPolicyManager.toggleSelection(policy, "잘 모르겠어요")
            }
        }

        lifecycleScope.launch {
            // 교통
            launch {
                viewModel.goodNewsTrafficManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = traffics,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.goodNewsTrafficManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.goodNewsTrafficManager)
                    }
                }
            }

            // 개발
            launch {
                viewModel.goodNewsDevelopmentManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = developments,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.goodNewsDevelopmentManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.goodNewsDevelopmentManager)
                    }
                }
            }

            // 교육
            launch {
                viewModel.goodNewsEducationManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = educations,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.goodNewsEducationManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.goodNewsEducationManager)
                    }
                }
            }

            // 자연환경
            launch {
                viewModel.goodNewsNaturalEnvironmentManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = naturalEnvironments,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.goodNewsNaturalEnvironmentManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.goodNewsNaturalEnvironmentManager)
                    }
                }
            }

            // 문화
            launch {
                viewModel.goodNewsCultureManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = cultures,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.goodNewsCultureManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.goodNewsCultureManager)
                    }
                }
            }

            // 산업
            launch {
                viewModel.goodNewsIndustryManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = industries,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.goodNewsIndustryManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.goodNewsIndustryManager)
                    }
                }
            }

            // 정책
            launch {
                viewModel.goodNewsPolicyManager.selectedItems.collect { selectedItems ->
                    updateMultiSelectionUI(
                        items = policies,
                        selectedItems = selectedItems,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.goodNewsPolicyManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.goodNewsPolicyManager)
                    }
                }
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            viewGoodNewsOverallReview.setOnClickListener {
                openWriteOverallReviewActivity()
            }
            tvGoodNewsReview.setOnClickListener {
                openWriteOverallReviewActivity()
            }
        }
    }

    private fun openWriteOverallReviewActivity() {
        overallReviewResult.launch(
            Intent(requireContext(), WriteOverallReviewActivity::class.java).apply {
                putExtra(
                    OVERALL_REVIEW_TITLE,
                    getString(info.imdang.component.R.string.good_news_review)
                )
                putExtra(
                    OVERALL_REVIEW,
                    this@WriteInsightGoodNewsFragment.viewModel.goodNewsReview.value
                )
            }
        )
    }

    private fun resetSelectionDialog(manager: SelectionManager) {
        val resetItem = "잘 모르겠어요"
        requireContext().showCommonDialog(
            message = getString(info.imdang.component.R.string.write_insight_unselect_message),
            positiveButtonText = getString(info.imdang.component.R.string.yes_its_ok),
            negativeButtonText = getString(info.imdang.component.R.string.cancel),
            onClickPositiveButton = { manager.confirmReset(resetItem) },
            onClickNegativeButton = { manager.cancelReset() }
        )
    }

    companion object {
        fun instance(): WriteInsightGoodNewsFragment = WriteInsightGoodNewsFragment()
    }
}
