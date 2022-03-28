package com.jihunyun.movieposterlotte

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jihunyun.movieposterlotte.api.ApiManager
import com.jihunyun.movieposterlotte.data.MoviePosterDataSet
import com.jihunyun.movieposterlotte.data.MoviePosterResponse
import com.jihunyun.movieposterlotte.data.ViewType
import com.jihunyun.movieposterlotte.utils.ResponseResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

typealias LoadingScreen = (Boolean) -> Unit
class MainViewModel : ViewModel() {
    private val compositeDisposable by lazy { CompositeDisposable() }

    val questionFirstResult: MutableLiveData<ResponseResult<List<MoviePosterDataSet>>> = MutableLiveData()
    val questionSecondResult: MutableLiveData<ResponseResult<List<MoviePosterDataSet>>> = MutableLiveData()
    var loadingScreen: LoadingScreen? = null

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun setLoading(isState: Boolean) {
        loadingScreen?.invoke(isState)
    }

    fun getMoviePosterData(keyword: String = "", isQuestionFirst: Boolean = false) {
        ApiManager.requestMoviePoster(keyword)
            .map { transformMoviePosterDataSet(it, isQuestionFirst) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (isQuestionFirst) questionFirstResult.value = ResponseResult.Success(it)
                else questionSecondResult.value = ResponseResult.Success(it)
            }, {
                if (isQuestionFirst) questionFirstResult.value = ResponseResult.Error(it.message)
                else questionSecondResult.value = ResponseResult.Error(it.message)
            }).addTo(compositeDisposable)
    }

    private fun transformMoviePosterDataSet(data: MoviePosterResponse, isQuestionFirst: Boolean): List<MoviePosterDataSet> {
        val moduleData = mutableListOf<MoviePosterDataSet>()
        data.getData()?.result?.forEach { result ->
            moduleData.add(
                MoviePosterDataSet(
                viewType = if (isQuestionFirst) ViewType.MOVIE_NO_IMG_ITEM_VIEW_TYPE else ViewType.MOVIE_ITEM_VIEW_TYPE,
                uniqueId = result.dOCID,
                imgList = result.getPosterImgs(),
                title = result.title,
                directorNm = result.directors?.getDirector(),
                releaseDate = result.getReleaseDate()
            ))
        }

        if (isQuestionFirst) moduleData.getOrNull(0)?.isSelected = true

        if (moduleData.isEmpty()) moduleData.add(MoviePosterDataSet(viewType = ViewType.EMPTY_VIEW_TYPE))
        return moduleData
    }
}