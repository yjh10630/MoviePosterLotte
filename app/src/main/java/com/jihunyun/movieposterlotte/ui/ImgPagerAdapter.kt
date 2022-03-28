package com.jihunyun.movieposterlotte.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jihunyun.movieposterlotte.R
import com.jihunyun.movieposterlotte.databinding.ViewImgItemBinding

class ImgPagerAdapter: ListAdapter<String, ImgPagerAdapter.ImgItemViewHolder>(object: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgItemViewHolder =
        ImgItemViewHolder(
            ViewImgItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    override fun onBindViewHolder(holder: ImgItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
    override fun getItemCount(): Int = Math.min(currentList.size, 5)

    inner class ImgItemViewHolder(private val binding: ViewImgItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(imgUrl: String?) {
            Glide.with(itemView.context)
                .load(imgUrl)
                .error(R.drawable.no_img)
                .into(binding.img)
        }
    }
}