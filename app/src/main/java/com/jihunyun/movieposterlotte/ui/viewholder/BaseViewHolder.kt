package com.jihunyun.movieposterlotte.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jihunyun.movieposterlotte.ui.PosterDataCallback

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(data: Any?)
}

abstract class NoImgBaseViewHolder(itemView: View): BaseViewHolder(itemView) {
    override fun onBind(data: Any?) {}
    abstract fun onBind(data: Any?, callback: PosterDataCallback?)
}