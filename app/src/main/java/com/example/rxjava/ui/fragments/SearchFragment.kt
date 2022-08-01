package com.example.rxjava.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxjava.R
import com.example.rxjava.app.App
import com.example.rxjava.databinding.FragmentSearchBinding
import com.example.rxjava.ui.adapters.PostersAdapter
import com.example.rxjava.viewmodels.details.DetailsViewModel
import com.example.rxjava.viewmodels.details.DetailsViewModelFactory
import com.example.rxjava.viewmodels.search.SearchViewModel
import com.example.rxjava.viewmodels.search.SearchViewModelFactory
import javax.inject.Inject


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PostersAdapter() }

    @Inject
    lateinit var vmFactory: SearchViewModelFactory

    private lateinit var sViewModel: SearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)

        sViewModel = ViewModelProvider(this, vmFactory)[SearchViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        setAdapter()

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                sViewModel.getAnimePostersFromSearch(p0.toString())
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                sViewModel.getAnimePostersFromSearch(p0.toString())
                return false
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()

    }

    private fun subscribeToLiveData() = with(sViewModel) {
        listAnimePosterAction.observe(this@SearchFragment) {
            adapter.submitList(it)
        }
        actionError.observe(this@SearchFragment) {
            Toast.makeText(
                context,
                getString(R.string.an_error_has_occurred, it),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setAdapter() = with(binding) {
        rvList.adapter = this@SearchFragment.adapter
        rvList.layoutManager = LinearLayoutManager(context)
    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}