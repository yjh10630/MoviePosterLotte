package com.jihunyun.movieposterlotte.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.jihunyun.movieposterlotte.R
import com.jihunyun.movieposterlotte.utils.toPx

class DotIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var mViewpager: ViewPager2
    private var mLastPosition = -1

    private var mIndicatorMargin = 3.toPx
    private var mIndicatorWidth = 6.toPx
    private var mIndicatorHeight = 6.toPx

    private val mSelectedDrawable = R.drawable.dot_indicator_on
    private val mUnSelectedDrawable = R.drawable.dot_indicator_off

    fun setCurrentIndexDot(position: Int) {
        if (childCount <= 1) return
        if (mLastPosition == position) return
        if (mLastPosition >= 0) {
            getChildAt(mLastPosition).setBackgroundResource(mUnSelectedDrawable)
        }
        getChildAt(position)?.let {
            it.setBackgroundResource(mSelectedDrawable)
            mLastPosition = position
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (::mViewpager.isInitialized) {
            mViewpager.adapter?.registerAdapterDataObserver(registerDataObserver)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (::mViewpager.isInitialized) {
            mViewpager.adapter?.unregisterAdapterDataObserver(registerDataObserver)
        }
    }

    private val registerDataObserver = object: RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            initIndicators()
            mLastPosition = 0
        }
    }

    fun setViewPager(viewPager: ViewPager2) {
        mViewpager = viewPager
        initIndicators()
    }
    
    private fun initIndicators() {
        if ((mViewpager.adapter?.itemCount ?: 0) > 1) {
            visibility = View.VISIBLE
            if (mViewpager.adapter != null) {
                mLastPosition = -1
                createIndicators()
            }
        } else {
            visibility = View.INVISIBLE
        }
    }

    private fun createIndicators() {
        removeAllViews()
        val count = mViewpager.adapter?.itemCount ?: 0
        if (count <= 1) return
        val currentItem = mViewpager.currentItem
        for (i in 0 until count) {
            if (currentItem == i) addIndicator(mSelectedDrawable)
            else addIndicator(mUnSelectedDrawable)
        }
    }

    private fun addIndicator(@DrawableRes backgroundDrawableId: Int) {
        val indicator = View(context)
        indicator.setBackgroundResource(backgroundDrawableId)
        addView(indicator, mIndicatorWidth, mIndicatorHeight)
        val lp = indicator.layoutParams as? LinearLayout.LayoutParams
        lp?.leftMargin = mIndicatorMargin
        lp?.rightMargin = mIndicatorMargin
        indicator.layoutParams = lp
    }
}