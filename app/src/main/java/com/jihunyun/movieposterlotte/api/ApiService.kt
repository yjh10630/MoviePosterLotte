package com.jihunyun.movieposterlotte.api

import com.jihunyun.movieposterlotte.data.MoviePosterResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&listCount=100")
    fun getMoviePoster(@Query("keyword") keyword: String?): Single<MoviePosterResponse>
}