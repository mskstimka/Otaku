package com.example.otaku.anime.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.usecases.GetAnimePostersFromSearchUseCase
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentSearchBinding
import com.example.otaku.anime.search.adapters.PostersAdapter
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PostersAdapter { hideIcon(it) } }

    @Inject
    lateinit var vmFactory: SearchViewModelFactory

    private lateinit var sViewModel: SearchViewModel

    @Inject
    lateinit var getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().applicationContext as App).appComponent.inject(this)
        sViewModel = ViewModelProvider(this, vmFactory)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        bindView()
        subscribeToFlow()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setAdapter() = with(binding) {
        rvFragmentSearchList.adapter = this@SearchFragment.adapter
        rvFragmentSearchList.layoutManager = LinearLayoutManager(context)
    }

    private fun hideIcon(visibility: Int) {
        binding.group.visibility = visibility
    }

    private fun bindView() = with(binding) {


        svFragmentSearchSearch.setOnClickListener {
            svFragmentSearchSearch.onActionViewExpanded()
        }

        svFragmentSearchSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                sViewModel.onTextChanged(p0.toString())
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun subscribeToFlow() = with(sViewModel) {
        subscribeToFlow(lifecycleOwner = viewLifecycleOwner, flow = actionSearch) {
            if (binding.svFragmentSearchSearch.query.toString() == "") {
                adapter.submitList(emptyList())
            } else {
                adapter.submitList(it)
            }
        }

        subscribeToFlow(lifecycleOwner = viewLifecycleOwner, flow = actionError) { message ->
            BannerUtils.showSnackBar(
                binding.root,
                message,
                requireContext(),
                requireActivity().findViewById(
                    R.id.navBottom
                )
            )
        }
    }

}


