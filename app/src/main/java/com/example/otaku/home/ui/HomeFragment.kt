package com.example.otaku.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentHomeBinding
import com.example.otaku.home.adapters.favorites.ContainerFavorites
import com.example.otaku.home.adapters.favorites.ContainerFavoritesAdapter
import com.example.otaku.home.adapters.genres.ContainerGenresList
import com.example.otaku.home.adapters.genres.ContainerGenresListAdapter
import com.example.otaku.home.adapters.poster.ContainerPoster
import com.example.otaku.home.adapters.poster.ContainerPosterAdapter
import com.example.otaku.home.adapters.random.ContainerRandomAdapter
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var hViewModel: HomeViewModel

    private val favoriteAdapter by lazy { ContainerFavoritesAdapter() }
    private val posterAdapter by lazy { ContainerPosterAdapter() }
    private val randomAdapter by lazy { ContainerRandomAdapter { refresh() } }
    private val genreAdapter by lazy { ContainerGenresListAdapter() }

    private val mergeAdapter by lazy {
        MergeAdapter(
            posterAdapter,
            favoriteAdapter,
            randomAdapter,
            genreAdapter
        )
    }

    init {
        posterAdapter.submitList(listOf(ContainerPoster {
            BannerUtils.showSnackBar(
                binding.root,
                "...",
                requireContext(),
                requireActivity().findViewById(
                    R.id.navBottom
                )
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

        initView()

        subscribeToFlow()

        return binding.root
    }

    private fun initView() = with(binding) {
        with(swipeToRefresh) {
            setOnRefreshListener {
                lifecycleScope.launch {
                    hViewModel.list.clear()
                    hViewModel.refresh(hViewModel.arrayPrefPosters)
                    delay(1000)
                    isRefreshing = false
                }
            }
        }
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
            flow = actionMessage,
            lifecycleOwner = viewLifecycleOwner
        ) { message ->
            BannerUtils.showSnackBar(
                binding.root,
                message,
                requireContext(),
                requireActivity().findViewById(
                    R.id.navBottom
                )
            )
        }

        subscribeToFlow(
            flow = actionAnimePosters,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            val list = mutableListOf<ContainerGenresList>()
            item.map {
                list.add(it)
            }
            genreAdapter.submitList(list)
        }

        subscribeToFlow(
            flow = actionAnimeScreenshots,
            lifecycleOwner = viewLifecycleOwner
        ) { list ->
            randomAdapter.setItemsScreenshots(list = list)
        }

        subscribeToFlow(
            flow = actionAnimeRandom,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            randomAdapter.submitList(item)
        }

        subscribeToFlow(
            flow = actionFavorites,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            if (item.isNotEmpty()) {
                favoriteAdapter.submitList(
                    listOf(
                        ContainerFavorites(
                            list = item
                        )
                    )
                )
            }
        }
    }
}

