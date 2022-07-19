package com.jordan.newsandroid.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordan.newsandroid.R
import com.jordan.newsandroid.adapter.NewsAdapter
import com.jordan.newsandroid.databinding.FragmentBreakingNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var breakingNewsViewModel: BreakingNewsViewModel

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding: FragmentBreakingNewsBinding
        get() = _binding!!

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onResume() {
        super.onResume()
        val countries = resources.getStringArray(R.array.countries)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, countries)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.autoCompleteTextView.addTextChangedListener {
            breakingNewsViewModel.onEvent(binding.autoCompleteTextView.text.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)

        breakingNewsViewModel = ViewModelProvider(requireActivity())[BreakingNewsViewModel::class.java]

        setUpRecyclerView()
        subscribeToObservers()

        return binding.root
    }

    private fun subscribeToObservers() {
        breakingNewsViewModel.newsItems.observe(viewLifecycleOwner) {
            newsAdapter.news = it
        }
    }

    private fun setUpRecyclerView() = binding.recyclerView.apply {
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}