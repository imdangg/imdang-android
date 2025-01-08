package info.imdang.imdang.ui.write.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseFragment
import info.imdang.imdang.common.util.SelectionManager
import info.imdang.imdang.common.util.SelectionUtils.updateSingleSelectionUI
import info.imdang.imdang.databinding.FragmentWriteInsightComplexEnvironmentBinding
import info.imdang.imdang.ui.common.showCommonDialog
import info.imdang.imdang.ui.write.WriteInsightViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WriteInsightComplexEnvironmentFragment :
    BaseFragment<FragmentWriteInsightComplexEnvironmentBinding>(
        R.layout.fragment_write_insight_complex_environment
    ) {

    private val viewModel by activityViewModels<WriteInsightViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()
    }

    private fun init() {
        with(binding) {
            viewModel = this@WriteInsightComplexEnvironmentFragment.viewModel
        }
    }

    private fun observe() {
        // 건물
        val buildings = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvBuildingNot,
            getString(info.imdang.component.R.string.best) to
                binding.tvBuildingBest,
            getString(info.imdang.component.R.string.great) to
                binding.tvBuildingGreat,
            getString(info.imdang.component.R.string.normal) to
                binding.tvBuildingNormal,
            getString(info.imdang.component.R.string.not_very_good) to
                binding.tvBuildingNotVeryGood
        )

        buildings.forEach { (building, textView) ->
            textView.setOnClickListener {
                viewModel.complexEnvironmentBuildingManager.toggleSelection(building, "잘 모르겠어요")
            }
        }

        // 안전
        val safeties = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvSafetyNot,
            getString(info.imdang.component.R.string.best) to
                binding.tvSafetyBest,
            getString(info.imdang.component.R.string.great) to
                binding.tvSafetyGreat,
            getString(info.imdang.component.R.string.normal) to
                binding.tvSafetyNormal,
            getString(info.imdang.component.R.string.not_very_good) to
                binding.tvSafetyNotVeryGood
        )

        safeties.forEach { (safety, textView) ->
            textView.setOnClickListener {
                viewModel.complexEnvironmentSafetyManager.toggleSelection(safety, "잘 모르겠어요")
            }
        }

        // 어린이 시설
        val childrenFacilities = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvChildrenFacilityNot,
            getString(info.imdang.component.R.string.best) to
                binding.tvChildrenFacilityBest,
            getString(info.imdang.component.R.string.great) to
                binding.tvChildrenFacilityGreat,
            getString(info.imdang.component.R.string.normal) to
                binding.tvChildrenFacilityNormal,
            getString(info.imdang.component.R.string.not_very_good) to
                binding.tvChildrenFacilityNotVeryGood
        )

        childrenFacilities.forEach { (childrenFacility, textView) ->
            textView.setOnClickListener {
                viewModel.complexEnvironmentChildrenFacilityManager
                    .toggleSelection(childrenFacility, "잘 모르겠어요")
            }
        }

        // 경로 시설
        val silverFacilities = mapOf(
            getString(info.imdang.component.R.string.i_am_not_sure) to
                binding.tvSilverFacilityNot,
            getString(info.imdang.component.R.string.best) to
                binding.tvSilverFacilityBest,
            getString(info.imdang.component.R.string.great) to
                binding.tvSilverFacilityGreat,
            getString(info.imdang.component.R.string.normal) to
                binding.tvSilverFacilityNormal,
            getString(info.imdang.component.R.string.not_very_good) to
                binding.tvSilverFacilityNotVeryGood
        )

        silverFacilities.forEach { (childrenFacility, textView) ->
            textView.setOnClickListener {
                viewModel.complexEnvironmentSilverFacilityManager
                    .toggleSelection(childrenFacility, "잘 모르겠어요")
            }
        }

        lifecycleScope.launch {
            // 건물
            launch {
                viewModel.complexEnvironmentBuildingManager.selectedItems.collect { selectedItems ->
                    val selectedItem = selectedItems.firstOrNull()
                    updateSingleSelectionUI(
                        items = buildings,
                        selectedItem = selectedItem,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.complexEnvironmentBuildingManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.complexEnvironmentBuildingManager)
                    }
                }
            }

            // 안전
            launch {
                viewModel.complexEnvironmentSafetyManager.selectedItems.collect { selectedItems ->
                    val selectedItem = selectedItems.firstOrNull()
                    updateSingleSelectionUI(
                        items = safeties,
                        selectedItem = selectedItem,
                        context = requireContext()
                    )
                }
            }

            launch {
                viewModel.complexEnvironmentSafetyManager.showResetDialog.collect { showDialog ->
                    if (showDialog) {
                        resetSelectionDialog(viewModel.complexEnvironmentSafetyManager)
                    }
                }
            }

            // 어린이 시설
            launch {
                viewModel.complexEnvironmentChildrenFacilityManager.selectedItems
                    .collect { selectedItems ->
                        val selectedItem = selectedItems.firstOrNull()
                        updateSingleSelectionUI(
                            items = childrenFacilities,
                            selectedItem = selectedItem,
                            context = requireContext()
                        )
                    }
            }

            launch {
                viewModel.complexEnvironmentChildrenFacilityManager.showResetDialog
                    .collect { showDialog ->
                        if (showDialog) {
                            resetSelectionDialog(
                                viewModel.complexEnvironmentChildrenFacilityManager
                            )
                        }
                    }
            }

            // 경로 시설
            launch {
                viewModel.complexEnvironmentSilverFacilityManager.selectedItems
                    .collect { selectedItems ->
                        val selectedItem = selectedItems.firstOrNull()
                        updateSingleSelectionUI(
                            items = silverFacilities,
                            selectedItem = selectedItem,
                            context = requireContext()
                        )
                    }
            }

            launch {
                viewModel.complexEnvironmentSilverFacilityManager.showResetDialog
                    .collect { showDialog ->
                        if (showDialog) {
                            resetSelectionDialog(viewModel.complexEnvironmentSilverFacilityManager)
                        }
                    }
            }
        }
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
        fun instance(): WriteInsightComplexEnvironmentFragment =
            WriteInsightComplexEnvironmentFragment()
    }
}
