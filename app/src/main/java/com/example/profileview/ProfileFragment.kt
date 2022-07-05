package com.example.profileview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profileview.component.LayoutHelper
import com.example.profileview.component.dp
import kotlin.math.ceil

class ProfileFragment : Fragment() {

    private lateinit var rootView: FrameLayout
    private lateinit var topView: SettingTopView
    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var settingAdapter: SettingAdapter
    private lateinit var versionNumber: TextView
    private var margin = 24

    companion object {
        lateinit var settingsRecyclerView: RecyclerView
        var expandHeight = 124.dp()
        val density = Resources.getSystem().displayMetrics.density
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = context?.let { FrameLayout(it) }!!
        rootView.setWillNotDraw(false)
        rootView.setBackgroundColor(Color.WHITE)

        settingsRecyclerView = RecyclerView(requireContext())
        settingsRecyclerView.isVerticalScrollBarEnabled = false
        settingsRecyclerView.setPadding(0, 200.dp(), 0, 120)
        settingAdapter = SettingAdapter(requireContext())
        settingAdapter.updateRows()
        settingsRecyclerView.adapter = settingAdapter
        settingsRecyclerView.clipToPadding = false
        settingsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        settingsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                onScrollChanged();
            }
        })
        rootView.addView(
            settingsRecyclerView,
            LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT
            )
        )

        topView = SettingTopView(context)
        topView.setBackgroundColor(resources.getColor(R.color.purple_700))
        rootView.addView(
            topView,
            LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT
            )
        )

        avatar = ImageView(context)
        avatar.setImageResource(R.drawable.ic_baseline_account_circle_24)
        rootView.addView(
            avatar,
            LayoutHelper.createFrame(
                72,
                72,
                Gravity.LEFT,
                45,
                6,
                12,
                12

            )
        )

        name = TextView(context)
        name.gravity = Gravity.LEFT
        name.textSize = 16F
        name.text = "First Name"
        name.setTextColor(Color.WHITE)
        name.maxLines = 1
        name.isSingleLine = true
        rootView.addView(
            name,
            LayoutHelper.createFrame(
                LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.LEFT,
                130,
                16,
                12,
                12

            )
        )

        phoneNumber = TextView(context)
        phoneNumber.setTextColor(Color.WHITE)
        phoneNumber.gravity = Gravity.START
        phoneNumber.text = "+98 9126926868"
        phoneNumber.textSize = 14F
        phoneNumber.maxLines = 1
        phoneNumber.isSingleLine = true
        phoneNumber.tag = "phoneNumber"
        rootView.addView(
            phoneNumber,
            LayoutHelper.createFrame(
                LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.START,
                130,
                38,
                margin,
                12

            )
        )

        versionNumber = TextView(context)
        versionNumber.gravity = Gravity.CENTER
        versionNumber.textSize = 14F
        versionNumber.text = "Build 435 - V 4.2.4"
        versionNumber.maxLines = 1
        versionNumber.isSingleLine = true
        versionNumber.setTextColor(Color.WHITE)
        versionNumber.setBackgroundColor(resources.getColor(R.color.purple_700))
        rootView.addView(
            versionNumber,
            LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT,
                32,
                Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM,
                0,
                780,
                0,
                0

            )
        )
        return rootView
    }

    private fun onScrollChanged() {
        val child = settingsRecyclerView.getChildAt(0)
        val holder: RecyclerView.ViewHolder? = settingsRecyclerView.findContainingViewHolder(child)
        val firstViewTop = child.top
        var scrolledTop = 0
        if (firstViewTop >= 0 && holder != null && holder.adapterPosition == 0)
            scrolledTop = firstViewTop

        if (expandHeight != scrolledTop) {
            expandHeight = scrolledTop
            topView.invalidate()
            changeListView()
        }
        if (firstViewTop > 255)
            topView.invalidateForce()
    }

    private fun changeListView() {
        val average = expandHeight / 44.dp().toFloat()
        val avatarY: Float = (1.0f + average) * density + 17 * density * average
        avatar.scaleX = (72 + 5 * average) / 72f
        avatar.scaleY = (72 + 5 * average) / 72f
        avatar.translationX = 7.dp() * -average
        avatar.translationY = ceil(avatarY)

        name.translationX = 7.dp() * -average
        name.translationY = avatarY - density + (density * average)

        phoneNumber.translationX = 7.dp() * -average
        phoneNumber.translationY = avatarY - density + (2 * density * average)
    }

    class SettingTopView(context: Context?) : View(context) {
        private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private var currentColor = 0
        fun invalidateForce() {
            invalidate()
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 200.dp());
        }

        override fun onDraw(canvas: Canvas?) {
            val height: Int = measuredHeight - 124.dp()
            val average = expandHeight / 44.dp().toFloat()
            val avatarY: Float = (1.0f + average) * density + 17 * density * average
            canvas?.drawRect(
                0F, 0F, measuredWidth.toFloat(),
                height + avatarY - density + (density * average) + 26, paint
            )
        }

        override fun setBackgroundColor(color: Int) {
            if (color != currentColor) {
                currentColor = color
                paint.color = currentColor
                invalidate()
            }
            super.setBackgroundColor(0)
        }
    }
}

