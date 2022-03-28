package com.jihunyun.movieposterlotte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jihunyun.movieposterlotte.databinding.ActivityMainBinding
import com.jihunyun.movieposterlotte.ui.QuestionFirstFragment
import com.jihunyun.movieposterlotte.ui.QuestionSecondFragment
import com.jihunyun.movieposterlotte.utils.isShow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class MainActivity : AppCompatActivity() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var isQuestionSecondShow = false

    private val questionFirstFragment: QuestionFirstFragment by lazy {
        QuestionFirstFragment.newInstance()
    }

    private val questionSecondFragment: QuestionSecondFragment by lazy {
        QuestionSecondFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.loadingScreen = ::setLoading


        initFragment()
        binding.changeQuestion.setOnClickListener {
            isQuestionSecondShow = !isQuestionSecondShow
            changeFloatBtn()
            changeFragment()
        }
    }

    private fun setLoading(isState: Boolean) {
        binding.loadingContainer.isShow(isState)
    }

    private fun changeFloatBtn() {
        binding.changeQuestionTxt.text = if (isQuestionSecondShow) "Q2" else "Q1"
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, questionFirstFragment)
            .add(R.id.frameLayout, questionSecondFragment)
            .show(questionFirstFragment)
            .hide(questionSecondFragment)
            .commitNow()
    }

    private fun changeFragment() {
        var tobeShow: Fragment? = null
        var tobeHide: Fragment? = null
        if (isQuestionSecondShow) {
            tobeShow = questionSecondFragment
            tobeHide = questionFirstFragment
        } else {
            tobeShow = questionFirstFragment
            tobeHide = questionSecondFragment
        }
        supportFragmentManager.beginTransaction()
            .show(tobeShow)
            .hide(tobeHide)
            .commitNow()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}