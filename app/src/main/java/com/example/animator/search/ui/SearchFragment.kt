package com.example.animator.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animator_domain.usecases.GetAnimePostersFromSearchUseCase
import com.example.animator.app.App
import androidx.appcompat.widget.SearchView
import com.example.animator.databinding.FragmentSearchBinding
import com.example.animator.search.adapters.PostersAdapter
import com.example.animator.utils.*
import javax.inject.Inject


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PostersAdapter() }

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

    private fun bindView() = with(binding) {
        svFragmentSearchSearch.setOnClickListener {
            binding.svFragmentSearchSearch.onActionViewExpanded()
        }

        svFragmentSearchSearch.setOnCloseListener {
            binding.svFragmentSearchSearch.onActionViewExpanded()
            false
        }

        svFragmentSearchSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == "") {
                    binding.group.visibility = View.VISIBLE

                    sViewModel.onTextChanged(p0.toString())
                } else {
                    binding.group.visibility = View.GONE
                    sViewModel.onTextChanged(p0.toString())
                }
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun subscribeToFlow() = with(sViewModel) {
        subscribeToFlow(lifecycleOwner = viewLifecycleOwner, flow = actionSearch) {
            if (binding.svFragmentSearchSearch.query != "") {
                adapter.submitList(it)
            }
        }

        subscribeToFlow(lifecycleOwner = viewLifecycleOwner, flow = actionError) { message ->
            BannerUtils.showToast(
                message,
                requireContext()
            )
        }
    }

}

