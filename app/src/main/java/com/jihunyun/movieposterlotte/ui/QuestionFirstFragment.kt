package com.jihunyun.movieposterlotte.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.jihunyun.movieposterlotte.MainViewModel
import com.jihunyun.movieposterlotte.databinding.FragmentQuestionFirstBinding
import com.jihunyun.movieposterlotte.utils.ResponseResult
import com.jihunyun.movieposterlotte.utils.VerticalMarginDecoration
import com.jihunyun.movieposterlotte.utils.toPx

typealias PosterDataCallback = (Int) -> Unit
class QuestionFirstFragment: Fragment() {

    companion object {
        fun newInstance(): QuestionFirstFragment {
            val args = Bundle().apply {}
            return QuestionFirstFragment().apply {
                arguments = args
            }
        }
    }

    private var _binding: FragmentQuestionFirstBinding? = null
    private val binding get() = _binding!!
    private val activityViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                MainViewModel() as T
        }).get(MainViewModel::class.java)
    }

    private val posterClickCallback: PosterDataCallback = { index ->

        val adapter = (binding.recyclerView.adapter as? MainListAdapter)

        val dataSet = adapter?.currentList?.map { it.copy() }?.toMutableList()
        val selectedItem = dataSet?.indexOfFirst { it.isSelected } ?: -1
        if (selectedItem != -1 && selectedItem != index) {
            dataSet?.getOrNull(selectedItem)?.isSelected = false
            dataSet?.getOrNull(index)?.isSelected = true

            adapter?.submitList(dataSet)
            (binding.viewPager.adapter as? ImgPagerAdapter)?.submitList(dataSet?.getOrNull(index)?.imgList)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserve()
        activityViewModel.setLoading(true)
        activityViewModel.getMoviePosterData(isQuestionFirst = true)
    }

    private fun initView() {
        with (binding) {
            viewPager.apply {
                adapter = ImgPagerAdapter()
                registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        indicator.setCurrentIndexDot(position)
                    }
                })
            }
            indicator.setViewPager(viewPager)

            keyword1.setOnClickListener {
                activityViewModel.setLoading(true)
                activityViewModel.getMoviePosterData("f", isQuestionFirst = true)
            }

            keyword2.setOnClickListener {
                activityViewModel.setLoading(true)
                activityViewModel.getMoviePosterData("아", isQuestionFirst = true)
            }

            keyword3.setOnClickListener {
                activityViewModel.setLoading(true)
                activityViewModel.getMoviePosterData("도", isQuestionFirst = true)
            }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = MainListAdapter(posterClickCallback)
                if (itemDecorationCount == 0) addItemDecoration(
                    VerticalMarginDecoration(firstItemMargin = 10.toPx, lastItemMargin = 10.toPx, itemMargin = 10.toPx)
                )
            }
        }
    }

    private fun initObserve() {
        with (activityViewModel) {
            questionFirstResult.observe(viewLifecycleOwner) {
                activityViewModel.setLoading(false)
                when(it) {
                    is ResponseResult.Success -> {
                        (binding.viewPager.adapter as? ImgPagerAdapter)?.submitList(it.value?.getOrNull(0)?.imgList)
                        (binding.recyclerView.adapter as? MainListAdapter)?.submitList(it.value)
                    }
                    is ResponseResult.Error -> {
                        Log.e("####", "Error > ${it.message}")
                        Toast.makeText(requireContext(), "일시적 오류로 인해 정보를 불러오지 못했습니다.\n잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}