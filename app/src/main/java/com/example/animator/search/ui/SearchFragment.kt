package com.example.animator.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animator_domain.usecases.GetAnimePostersFromSearchUseCase
import com.example.animator.R
import com.example.animator.app.App
import com.example.animator.databinding.FragmentSearchBinding
import com.example.animator.details.ui.DetailsFragment
import com.example.animator.search.adapters.PostersAdapter
import com.example.animator.utils.*
import javax.inject.Inject


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        PostersAdapter { posterId ->
            openDetailsPage(posterId = posterId)
        }
    }

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

        if (savedInstanceState != null) {
            binding.gifFragmentSearchLogo.alpha = savedInstanceState.getFloat(GIF_KEY)
            binding.tvFragmentSearchTitle.alpha = savedInstanceState.getFloat(TITLE_KEY)
            binding.tvFragmentSearchTitleDescription.alpha =
                savedInstanceState.getFloat(DESCRIPTION_KEY)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (_binding != null) {
            outState.putFloat(GIF_KEY, binding.gifFragmentSearchLogo.alpha)
            outState.putFloat(TITLE_KEY, binding.tvFragmentSearchTitle.alpha)
            outState.putFloat(DESCRIPTION_KEY, binding.tvFragmentSearchTitleDescription.alpha)
        }
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

        svFragmentSearchSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == "") {
                    startAnim()
                    sViewModel.onTextChanged(p0.toString())
                } else {
                    endAnim()
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

    fun startAnim() = with(binding) {
        AnimatorUtils.toStartView(
            requireContext(),
            gifFragmentSearchLogo,
            tvFragmentSearchTitle,
            tvFragmentSearchTitleDescription
        )
        binding.rvFragmentSearchList.visibility = RecyclerView.INVISIBLE
    }

    fun endAnim() = with(binding) {
        AnimatorUtils.toCloseView(
            requireContext(),
            gifFragmentSearchLogo,
            tvFragmentSearchTitle,
            tvFragmentSearchTitleDescription
        )

        binding.rvFragmentSearchList.visibility = RecyclerView.VISIBLE
    }

    private fun openDetailsPage(posterId: Int) {
        if (resources.getBoolean(R.bool.isTablet)) {
            parentFragmentManager.beginTransaction()
                .add(
                    R.id.tabletContainer, DetailsFragment.newInstance(posterId = posterId),
                    DetailsFragment.TAG_FRAGMENT
                )
                .commit()
        } else {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                    id = posterId
                )
            )
        }
    }

    companion object {
        private const val GIF_KEY = "GIF KEY"
        private const val TITLE_KEY = "TITLE KEY"
        private const val DESCRIPTION_KEY = "DESCRIPTION KEY"
    }
}

