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
import info.imdang.imdang.common.ext.inSightAptAddressValidation
import info.imdang.imdang.common.ext.inSightDateValidation
import info.imdang.imdang.common.ext.insightTitleValidation
import info.imdang.imdang.common.util.SelectionUtils.setupSelectionHandlers
import info.imdang.imdang.common.util.SelectionUtils.updateMultiSelectionUI
import info.imdang.imdang.common.util.SelectionUtils.updateSingleSelectionUI
import info.imdang.imdang.databinding.FragmentWriteInsightBasicInfoBinding
import info.imdang.imdang.ui.write.WriteInsightViewModel
import info.imdang.imdang.ui.write.address.KakaoAddressActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WriteInsightBasicInfoFragment :
    BaseFragment<FragmentWriteInsightBasicInfoBinding>(R.layout.fragment_write_insight_basic_info) {

    private val viewModel by activityViewModels<WriteInsightViewModel>()

    private val addressResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val roadAddress = result.data?.getStringExtra("roadAddress").orEmpty()
            val aptName = result.data?.getStringExtra("aptName").orEmpty()

            binding.etAptAddress.setText(roadAddress)
            binding.etAptName.setText(aptName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()
    }

    private fun init() {
        with(binding) {
            viewModel = this@WriteInsightBasicInfoFragment.viewModel

            tilTitle.setEndIconOnClickListener {
                etTitle.setText("")
                etTitle.requestFocus()
            }

            etTitle.setOnFocusChangeListener { _, hasFocus ->
                this@WriteInsightBasicInfoFragment.viewModel.updateInsightTitleFocused(hasFocus)
            }

            etTitle.insightTitleValidation(
                maxLength = 20,
                targetTextView = tvTitleCount,
                onValidStateChanged = { isValid ->
                    this@WriteInsightBasicInfoFragment.viewModel.updateInsightTitleValid(isValid)
                    this@WriteInsightBasicInfoFragment.viewModel
                        .updateInsightTitleCheckImageVisible(false)
                }
            )

            etAptAddress.setOnClickListener {
                val intent = Intent(requireContext(), KakaoAddressActivity::class.java)
                addressResultLauncher.launch(intent)
            }

            etAptAddress.inSightAptAddressValidation(
                targetImageView = ivCheckAptAddress,
                onValidStateChanged = { isValid ->
                    this@WriteInsightBasicInfoFragment.viewModel.updateAptAddressValid(isValid)
                }
            )

            tilDate.setEndIconOnClickListener {
                etDate.setText("")
                etDate.requestFocus()
            }

            etDate.setOnFocusChangeListener { _, hasFocus ->
                this@WriteInsightBasicInfoFragment.viewModel.updateInsightDateFocused(hasFocus)
            }

            etDate.inSightDateValidation(
                errorTextView = tvDateError,
                statusImageView = ivCheckDate,
                invalidIconResId = info.imdang.component.R.drawable.ic_exclamation_mark_red,
                parentLayout = tilDate,
                onValidStateChanged = { isValid ->
                    this@WriteInsightBasicInfoFragment.viewModel.updateInsightDateValid(isValid)
                }
            )
        }
    }

    private fun observe() {
        val times = mapOf(
            getString(info.imdang.component.R.string.morning) to binding.tvTimeMorning,
            getString(info.imdang.component.R.string.day) to binding.tvTimeDay,
            getString(info.imdang.component.R.string.evening) to binding.tvTimeEvening,
            getString(info.imdang.component.R.string.night) to binding.tvTimeNight
        )

        val traffics = mapOf(
            getString(info.imdang.component.R.string.car) to binding.tvTrafficCar,
            getString(info.imdang.component.R.string.public_traffic) to binding.tvTrafficPublic,
            getString(info.imdang.component.R.string.walk) to binding.tvTrafficWalk
        )

        val entrances = mapOf(
            getString(info.imdang.component.R.string.limited) to binding.tvEntranceLimited,
            getString(info.imdang.component.R.string.need_permission) to
                binding.tvEntranceNeedPermission,
            getString(info.imdang.component.R.string.free_entrance) to binding.tvEntranceFree
        )

        times.forEach { (time, textView) ->
            textView.setOnClickListener {
                viewModel.toggleTimeSelection(time)
            }
        }

        traffics.forEach { (traffic, textView) ->
            textView.setOnClickListener {
                viewModel.toggleTrafficSelection(traffic)
            }
        }

        setupSelectionHandlers(times, viewModel::toggleTimeSelection)
        setupSelectionHandlers(traffics, viewModel::toggleTrafficSelection)
        setupSelectionHandlers(entrances, viewModel::selectEntrance)

        lifecycleScope.launch {
            launch {
                viewModel.insightSelectedTimes.collect { selectedTimes ->
                    updateMultiSelectionUI(
                        items = times,
                        selectedItems = selectedTimes,
                        context = requireContext()
                    )
                }
            }
            launch {
                viewModel.insightSelectedTraffics.collect { selectedTraffics ->
                    updateMultiSelectionUI(
                        items = traffics,
                        selectedItems = selectedTraffics,
                        context = requireContext()
                    )
                }
            }
            launch {
                viewModel.insightSelectedEntrances.collect { selectedEntrance ->
                    updateSingleSelectionUI(
                        items = entrances,
                        selectedItem = selectedEntrance,
                        context = requireContext()
                    )
                }
            }
        }
    }

    fun clearEditTextFocus() {
        with(binding) {
            etTitle.clearFocus()
            etDate.clearFocus()
        }
    }

    fun updateDateIcon(isVisible: Boolean) {
        if (isVisible) {
            binding.ivCheckDate.setImageResource(info.imdang.component.R.drawable.ic_check)
        }
    }

    companion object {
        fun instance(): WriteInsightBasicInfoFragment = WriteInsightBasicInfoFragment()
    }
}
