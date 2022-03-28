package com.jihunyun.movieposterlotte.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jihunyun.movieposterlotte.data.MoviePosterDataSet
import com.jihunyun.movieposterlotte.data.ViewType
import com.jihunyun.movieposterlotte.databinding.ViewEmptyItemBinding
import com.jihunyun.movieposterlotte.databinding.ViewMoviePosterItemBinding
import com.jihunyun.movieposterlotte.databinding.ViewMoviePosterNoImgItemBinding
import com.jihunyun.movieposterlotte.ui.viewholder.*

class MainListAdapter(
    private val callback: PosterDataCallback? = null
): ListAdapter<MoviePosterDataSet, BaseViewHolder>(object: DiffUtil.ItemCallback<MoviePosterDataSet>() {
    override fun areItemsTheSame(oldItem: MoviePosterDataSet, newItem: MoviePosterDataSet): Boolean = oldItem.uniqueId == newItem.uniqueId
    override fun areContentsTheSame(oldItem: MoviePosterDataSet, newItem: MoviePosterDataSet): Boolean = oldItem == newItem
}) {
    override fun getItemViewType(position: Int): Int = getItem(position)?.viewType?.ordinal ?: ViewType.EMPTY_VIEW_TYPE.ordinal
    override fun getItemCount(): Int = Math.min(currentList.size, 100)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (ViewType.values()[viewType]) {
            ViewType.MOVIE_ITEM_VIEW_TYPE -> ItemViewHolder(
                ViewMoviePosterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            ViewType.MOVIE_NO_IMG_ITEM_VIEW_TYPE -> NoImgItemViewHolder(
                ViewMoviePosterNoImgItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> EmptyItemViewHolder(
                ViewEmptyItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(getItem(position))
        (holder as? NoImgBaseViewHolder)?.onBind(getItem(position), callback)
    }
}