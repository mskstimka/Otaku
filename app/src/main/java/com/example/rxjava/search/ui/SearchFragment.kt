package com.example.rxjava.search.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.a16_rxjava_domain.usecases.GetAnimePostersFromSearchUseCase
import com.example.rxjava.R
import com.example.rxjava.app.App
import com.example.rxjava.app.utils.BannerUtils
import com.example.rxjava.databinding.FragmentSearchBinding
import com.example.rxjava.search.adapters.PostersAdapter
import com.example.rxjava.utils.RxSearchObservable
import javax.inject.Inject


class SearchFragment : Fragment(), SearchContract.View<List<AnimePosterEntity>> {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PostersAdapter() }


    @Inject
    lateinit var getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase

    private lateinit var presenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)

        presenter = SearchPresenter(
            getAnimePostersFromSearchUseCase,
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        onSearch()

        binding.svSearch.setOnClickListener {
            binding.svSearch.onActionViewExpanded()
        }

        binding.svSearch.setOnCloseListener {
            binding.svSearch.onActionViewExpanded()
            false
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }


    @SuppressLint("CheckResult")
    private fun onSearch() {
        RxSearchObservable.fromView(binding.svSearch)
            .subscribe {
                presenter.getAnimePostersFromSearch(it)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapter() = with(binding) {
        rvList.adapter = this@SearchFragment.adapter
        rvList.layoutManager = LinearLayoutManager(context)
    }


    override fun updateViewData(result: Results<List<AnimePosterEntity>>) {

        when (result) {
            is Results.Success -> {
                adapter.submitList(result.data)
            }
            is Results.Error -> {
                BannerUtils.showToastError(
                    getString(R.string.an_error_has_occurred, result.exception),
                    requireContext()
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }
}

