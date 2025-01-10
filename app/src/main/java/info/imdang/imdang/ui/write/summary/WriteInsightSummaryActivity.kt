package info.imdang.imdang.ui.write.summary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.bindingadapter.bindVisible
import info.imdang.imdang.databinding.ActivityWriteInsightSummaryBinding

@AndroidEntryPoint
class WriteInsightSummaryActivity :
    BaseActivity<ActivityWriteInsightSummaryBinding>(R.layout.activity_write_insight_summary) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListener()
        setupExtra()
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            etInsightSummary.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                @SuppressLint("SetTextI18n")
                override fun afterTextChanged(s: Editable?) {
                    val currentLength = s?.length ?: 0
                    val isValid = currentLength >= 30

                    tvInsightSummaryCount.run {
                        bindVisible(currentLength > 0)
                        text = " ($currentLength/200)"
                    }

                    tvInsightSummaryConfirm.isEnabled = isValid
                }
            })
            tvInsightSummaryConfirm.setOnClickListener {
                setResult(
                    RESULT_OK,
                    Intent().apply {
                        putExtra(INSIGHT_SUMMARY, etInsightSummary.text.toString())
                    }
                )
                finish()
            }
        }
    }

    private fun setupExtra() {
        intent.getStringExtra(INSIGHT_SUMMARY)?.let { insightSummary ->
            if (insightSummary.isNotBlank()) {
                with(binding.etInsightSummary) {
                    setText(insightSummary)
                    setSelection(insightSummary.length)
                }
            }
        }
    }

    companion object {
        const val INSIGHT_SUMMARY = "insight_summary"
    }
}
