package info.imdang.imdang.ui.write.review

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.bindingadapter.bindVisible
import info.imdang.imdang.databinding.ActivityWriteOverallReviewBinding

@AndroidEntryPoint
class WriteOverallReviewActivity :
    BaseActivity<ActivityWriteOverallReviewBinding>(R.layout.activity_write_overall_review) {

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
            etOverallReview.addTextChangedListener(object : TextWatcher {
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
                    val isValid = currentLength !in 1..29

                    tvOverallReviewCount.run {
                        bindVisible(currentLength > 0)
                        text = " ($currentLength/200)"
                    }

                    tvOverallReviewConfirm.isEnabled = isValid
                }
            })
            tvOverallReviewConfirm.setOnClickListener {
                setResult(
                    RESULT_OK,
                    Intent().apply {
                        putExtra(OVERALL_REVIEW, etOverallReview.text.toString())
                    }
                )
                finish()
            }
        }
    }

    private fun setupExtra() {
        intent.getStringExtra(OVERALL_REVIEW_TITLE)?.let { title ->
            with(binding) {
                tvOverallReviewTitle.text = title
                tvOverallReviewSubject.text = title
            }
        }
        intent.getStringExtra(OVERALL_REVIEW)?.let { insightSummary ->
            if (insightSummary.isNotBlank()) {
                with(binding.etOverallReview) {
                    setText(insightSummary)
                    setSelection(insightSummary.length)
                }
            }
        }
    }

    companion object {
        const val OVERALL_REVIEW_TITLE = "overall_review_title"
        const val OVERALL_REVIEW = "overall_review"
    }
}
