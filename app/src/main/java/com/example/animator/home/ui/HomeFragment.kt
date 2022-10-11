package com.example.animator.home.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.animator.R
import com.example.animator.app.App
import com.example.animator.databinding.FragmentHomeBinding
import com.example.animator.home.adapters.genres.ContainerGenresList
import com.example.animator.home.adapters.genres.ContainerGenresListAdapter
import com.example.animator.home.adapters.poster.ContainerPoster
import com.example.animator.home.adapters.poster.ContainerPosterAdapter
import com.example.animator.home.adapters.random.ContainerRandomAdapter
import com.example.animator.utils.BannerUtils
import com.example.animator.utils.subscribeToFlow
import com.example.animator_domain.ARRAY_PREV_POSTERS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: HomeViewModelFactory

    private lateinit var hViewModel: HomeViewModel

    private val posterAdapter by lazy { ContainerPosterAdapter() }
    private val randomAdapter by lazy { ContainerRandomAdapter { refresh() } }
    private val genreAdapter by lazy { ContainerGenresListAdapter() }

    private val mergeAdapter by lazy { MergeAdapter(posterAdapter, randomAdapter, genreAdapter) }

    init {
        posterAdapter.submitList(listOf(ContainerPoster {
            BannerUtils.showToast(
                getString(R.string.ukraine_message),
                requireContext()
            )
        }))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        (requireActivity().applicationContext as App).appComponent.inject(this)

        hViewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]

        with(binding.swipeToRefresh) {
            setOnRefreshListener {
                lifecycleScope.launch {
                    hViewModel.list.clear()
                    hViewModel.responses.clear()
                    hViewModel.refresh(ARRAY_PREV_POSTERS)
                    delay(1000)
                    isRefreshing = false
                }
            }
        }

        subscribeToFlow()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun refresh() = hViewModel.getAnimeRandomPoster()

    private fun initAdapter() = with(binding) {
        recyclerView.adapter = mergeAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun subscribeToFlow() = with(hViewModel) {

        subscribeToFlow(
            flow = actionError,
            lifecycleOwner = viewLifecycleOwner
        ) { message ->
            BannerUtils.showToast(
                message,
                requireContext()
            )
        }

        subscribeToFlow(
            flow = pageAnimePosterAction,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            val list = mutableListOf<ContainerGenresList>()
            item.map {
                list.add(it)
            }
            genreAdapter.submitList(list)
            Log.d("UPDATED", "GENRES------------------------")
        }

        subscribeToFlow(
            flow = pageAnimeScreenshotsAction,
            lifecycleOwner = viewLifecycleOwner
        ) { list ->
            randomAdapter.setItemsScreenshots(list = list)
        }

        subscribeToFlow(
            flow = pageAnimeRandomAction,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            randomAdapter.submitList(item)
        }
    }
}

