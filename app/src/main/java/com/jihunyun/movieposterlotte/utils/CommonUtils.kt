package com.jihunyun.movieposterlotte.utils

import android.content.res.Resources
import android.view.View
import android.widget.RelativeLayout
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

inline val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun getDateFormat(input: String): String? {
    var output = ""
    try {
        val date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(input)
        output = SimpleDateFormat("yyyy년MM월dd일", Locale.getDefault()).format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    } finally {
        return output
    }
}

infix fun RelativeLayout?.isShow(isShow: Boolean) {
    this?.visibility = if(isShow) View.VISIBLE else View.GONE
}