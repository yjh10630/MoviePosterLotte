package com.jihunyun.movieposterlotte.data

data class MoviePosterDataSet (
    val viewType: ViewType = ViewType.MOVIE_ITEM_VIEW_TYPE,
    val uniqueId: String? = null,
    val imgList: List<String>? = null,
    val title: String? = null,
    val directorNm: String? = null,
    val releaseDate: String? = null,
    var isSelected: Boolean = false
)

enum class ViewType {
    EMPTY_VIEW_TYPE,
    MOVIE_ITEM_VIEW_TYPE,
    MOVIE_NO_IMG_ITEM_VIEW_TYPE
}