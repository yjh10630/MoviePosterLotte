package com.jihunyun.movieposterlotte.api

import androidx.viewbinding.BuildConfig
import com.jihunyun.movieposterlotte.data.MoviePosterResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiManager {

    private const val BASE_URL = "http://api.koreafilm.or.kr/"
    private const val SERVICE_KEY = "AGJG81FVHGF0X26L28W3"

    private lateinit var apiService: ApiService
    private fun apiService(): ApiService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        return apiService
    }

    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )

    private val interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val url =
            originalHttpUrl
                .newBuilder()
                .addQueryParameter("ServiceKey", SERVICE_KEY)
                .build()
        request.url(url)
        chain.proceed(request.build())
    }

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(logging)
        .connectTimeout(10000, TimeUnit.MILLISECONDS)
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .writeTimeout(10000, TimeUnit.MILLISECONDS)


    /**
     * Api request
     */
    fun requestMoviePoster(keyword: String): Single<MoviePosterResponse> =
        apiService().getMoviePoster(keyword).subscribeOn(Schedulers.io())

}