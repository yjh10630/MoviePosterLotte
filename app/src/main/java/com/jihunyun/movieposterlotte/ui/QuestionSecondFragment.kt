package com.jihunyun.movieposterlotte.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.jihunyun.movieposterlotte.MainViewModel
import com.jihunyun.movieposterlotte.data.ViewType
import com.jihunyun.movieposterlotte.databinding.FragmentQuestionSecondBinding
import com.jihunyun.movieposterlotte.utils.GridMarginEdgeIncludeDecoration
import com.jihunyun.movieposterlotte.utils.ResponseResult
import com.jihunyun.movieposterlotte.utils.toPx

class QuestionSecondFragment: Fragment() {

    companion object {
        fun newInstance(): QuestionSecondFragment {
            val args = Bundle().apply {}
            return QuestionSecondFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var mInputMethodManager: InputMethodManager
    private var _binding: FragmentQuestionSecondBinding? = null
    private val binding get() = _binding!!
    private val activityViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                MainViewModel() as T
        }).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionSecondBinding.inflate(inflater, container, false)
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
        activityViewModel.getMoviePosterData()
    }

    private fun initView() {
        with (binding) {

            search.apply {
                setOnKeyListener { _, keyCode, event ->
                    if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        searchKeyword(search.text.toString())
                        return@setOnKeyListener true
                    }
                    return@setOnKeyListener false
                }
                doAfterTextChanged {
                    searchRemove.visibility = if (it.toString().trim().isEmpty()) View.GONE else View.VISIBLE
                }
            }

            searchRemove.setOnClickListener {
                search.setText("")
            }

            searchIcon.setOnClickListener {
                searchKeyword(search.text.toString())
            }

            recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 2).apply {
                    spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val emptyView = (adapter as? MainListAdapter)?.currentList?.firstOrNull { it.viewType == ViewType.EMPTY_VIEW_TYPE }
                            return if (emptyView != null) 2
                            else 1
                        }
                    }
                }
                adapter = MainListAdapter()

                if (itemDecorationCount == 0)
                    addItemDecoration(GridMarginEdgeIncludeDecoration(10.toPx))

                setOnTouchListener { v, event ->
                    hideKeyboard()
                    false
                }
            }
        }
    }

    private fun searchKeyword(keyword: String) {
        val trimKeyword = keyword.trim()
        if (trimKeyword.isEmpty()) {
            Toast.makeText(requireContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
            binding.search.setText("")
        } else {
            activityViewModel.setLoading(true)
            activityViewModel.getMoviePosterData(trimKeyword)
            hideKeyboard()
        }
    }

    private fun initObserve() {
        with (activityViewModel) {
            questionSecondResult.observe(viewLifecycleOwner) {
                activityViewModel.setLoading(false)
                when(it) {
                    is ResponseResult.Success -> {
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

    private fun hideKeyboard() {
        requireActivity().currentFocus?.let {
            if (!::mInputMethodManager.isInitialized) {
                mInputMethodManager = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            mInputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }

}