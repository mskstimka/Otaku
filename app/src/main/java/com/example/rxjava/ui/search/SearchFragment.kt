package com.example.rxjava.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.rxjava.R
import com.example.rxjava.app.App
import com.example.rxjava.databinding.FragmentSearchBinding
import com.example.rxjava.ui.adapters.PostersAdapter
import javax.inject.Inject


class SearchFragment : Fragment(), SearchContract.View {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PostersAdapter() }


    @Inject
    lateinit var presenter: SearchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        setAdapter()

        initView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapter() = with(binding) {
        rvList.adapter = this@SearchFragment.adapter
        rvList.layoutManager = LinearLayoutManager(context)
    }

    override fun initView() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                presenter.loadData(p0.toString())
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                presenter.loadData(p0.toString())
                return false
            }
        })
    }

    override fun updateViewData(result: Results<*>) {

        when (result) {
            is Results.Success -> {
                adapter.submitList(presenter.list)
            }
            is Results.Error -> {
                Toast.makeText(
                    context,
                    requireActivity().getString(R.string.an_error_has_occurred, result.exception),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}

