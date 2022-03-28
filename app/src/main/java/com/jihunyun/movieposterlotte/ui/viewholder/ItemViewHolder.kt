package com.jihunyun.movieposterlotte.ui.viewholder

import androidx.viewpager2.widget.ViewPager2
import com.jihunyun.movieposterlotte.data.MoviePosterDataSet
import com.jihunyun.movieposterlotte.databinding.ViewMoviePosterItemBinding
import com.jihunyun.movieposterlotte.ui.ImgPagerAdapter

class ItemViewHolder(private val binding: ViewMoviePosterItemBinding): BaseViewHolder(binding.root) {
    override fun onBind(data: Any?) {
        (data as? MoviePosterDataSet)?.let {
            initView(it)
        }
    }

    private fun initView(data: MoviePosterDataSet) {
        with (binding) {
            viewPager.apply {
                adapter = ImgPagerAdapter().apply {
                    submitList(data.imgList)
                }
                registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        indicator.setCurrentIndexDot(position)
                    }
                })
            }

            indicator.setViewPager(viewPager)

            title.text = data.title?.trim()
            director.text = data.directorNm?.trim()
            releaseDate.text = data.releaseDate
        }
    }
}