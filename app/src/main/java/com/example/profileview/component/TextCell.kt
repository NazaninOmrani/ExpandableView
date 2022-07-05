package com.example.profileview.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView

@SuppressLint("ViewConstructor")
class TextCell(context: Context) : FrameLayout(context) {

    private var textView: TextView
    private var needDivider = false
    private val margin: Int = 24

    init {
        textView = TextView(context)
        textView.textSize = 14F
        textView.setTextColor(Color.GRAY)
        addView(
            textView,
            LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER_VERTICAL or Gravity.START, margin, 0, margin, 0
            )
        )

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(64.dp(), MeasureSpec.EXACTLY)
        )
    }

    fun setText(text: String?, divider: Boolean) {
        textView.text = text
        needDivider = divider
        setWillNotDraw(!needDivider)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        if (needDivider) {
            canvas.drawLine(
                20.dp().toFloat(),
                (measuredHeight - 1).toFloat(),
                measuredWidth.toFloat(),
                (measuredHeight - 1).toFloat(),
                paint
            )
        }
    }
}