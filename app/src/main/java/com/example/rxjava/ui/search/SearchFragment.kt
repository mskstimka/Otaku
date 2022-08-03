package com.example.rxjava.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.a16_rxjava_domain.usecases.GetAnimePostersFromSearchUseCase
import com.example.rxjava.R
import com.example.rxjava.app.App
import com.example.rxjava.databinding.FragmentSearchBinding
import com.example.rxjava.ui.adapters.PostersAdapter
import com.example.rxjava.utils.RxSearchObservable
import javax.inject.Inject


class SearchFragment : Fragment(), SearchContract.View<List<AnimePosterEntity>> {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PostersAdapter() }


    @Inject
    lateinit var getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase

    lateinit var presenter: SearchPresenter

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
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        setAdapter()

        binding.svSearch.query
        onSearch()

        return binding.root
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

    override fun initView() {

    }

    override fun updateViewData(result: Results<List<AnimePosterEntity>>) {

        when (result) {
            is Results.Success -> {
                adapter.submitList(result.data)
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}

