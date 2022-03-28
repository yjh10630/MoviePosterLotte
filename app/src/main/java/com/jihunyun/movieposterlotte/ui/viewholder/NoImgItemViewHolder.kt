package com.jihunyun.movieposterlotte.ui.viewholder

import android.graphics.Color
import com.jihunyun.movieposterlotte.data.MoviePosterDataSet
import com.jihunyun.movieposterlotte.databinding.ViewMoviePosterNoImgItemBinding
import com.jihunyun.movieposterlotte.ui.PosterDataCallback

class NoImgItemViewHolder(private val binding: ViewMoviePosterNoImgItemBinding): NoImgBaseViewHolder(binding.root) {
    override fun onBind(data: Any?, callback: PosterDataCallback?) {
        (data as? MoviePosterDataSet)?.let {
            initView(it, callback)
        }
    }

    private fun initView(data: MoviePosterDataSet, callback: PosterDataCallback?) {
        with (binding) {
            title.text = data.title?.trim()
            director.text = data.directorNm?.trim()
            releaseDate.text = data.releaseDate
            container.setBackgroundColor(Color.parseColor(
                if (data.isSelected) "#33CC0000" else "#ffffff"
            ))
        }

        itemView.setOnClickListener {
            callback?.invoke(adapterPosition)
        }
    }
}