package info.imdang.imdang.ui.write.summary

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.bindingadapter.bindVisible
import info.imdang.imdang.common.ext.setMargin
import info.imdang.imdang.databinding.ActivityWriteInsightSummaryBinding

@AndroidEntryPoint
class WriteInsightSummaryActivity :
    BaseActivity<ActivityWriteInsightSummaryBinding>(R.layout.activity_write_insight_summary) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("기본정보 요약")
        setupListener()
        setupExtra()
    }

    override fun onShowKeyboard(keyboardHeight: Int) {
        super.onShowKeyboard(keyboardHeight)

        binding.svWriteInsightSummary.fullScroll(View.FOCUS_DOWN)

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
            etInsightSummary.setOnTouchListener { view, motionEvent ->
                view.parent.requestDisallowInterceptTouchEvent(true)
                if (motionEvent.action == MotionEvent.ACTION_UP) {
                    view.parent.requestDisallowInterceptTouchEvent(false)
                }
                false
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
                    setButtonDrawable(0f)
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

    private fun setButtonDrawable(cornerRadius: Float) {
        with(binding.tvInsightSummaryConfirm) {
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
        const val INSIGHT_SUMMARY = "insight_summary"
    }
}
