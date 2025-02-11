package info.imdang.imdang.ui.write.review

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.bindingadapter.bindVisible
import info.imdang.imdang.common.ext.setMargin
import info.imdang.imdang.databinding.ActivityWriteOverallReviewBinding

@AndroidEntryPoint
class WriteOverallReviewActivity :
    BaseActivity<ActivityWriteOverallReviewBinding>(R.layout.activity_write_overall_review) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(OVERALL_REVIEW_TITLE)?.let(::setName)
        setupListener()
        setupExtra()
    }

    override fun onShowKeyboard(keyboardHeight: Int) {
        super.onShowKeyboard(keyboardHeight)

        setButtonDrawable(cornerRadius = 0f)
    }

    override fun onHideKeyboard() {
        super.onHideKeyboard()

        setButtonDrawable(
            cornerRadius = resources.getDimension(
                info.imdang.component.R.dimen.default_corner_radius
            )
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            etOverallReview.setOnTouchListener { view, motionEvent ->
                view.parent.requestDisallowInterceptTouchEvent(true)
                if (motionEvent.action == MotionEvent.ACTION_UP) {
                    view.parent.requestDisallowInterceptTouchEvent(false)
                }
                false
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
                    setButtonDrawable(0f)
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

    private fun setButtonDrawable(cornerRadius: Float) {
        with(binding.tvOverallReviewConfirm) {
            setMargin(
                left = if (cornerRadius == 0f) 0 else 20,
                right = if (cornerRadius == 0f) 0 else 20,
                top = 0,
                bottom = if (cornerRadius == 0f) 0 else 40
            )
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(
                    if (isEnabled) {
                        getColor(info.imdang.component.R.color.orange_500)
                    } else {
                        getColor(info.imdang.component.R.color.gray_100)
                    }
                )
                this.cornerRadius = cornerRadius
            }
        }
    }

    companion object {
        const val OVERALL_REVIEW_TITLE = "overall_review_title"
        const val OVERALL_REVIEW = "overall_review"
    }
}
