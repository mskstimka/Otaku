package com.example.otaku.anime.details.episodes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.animator_domain.NOT_FOUND_TEXT
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentEpisodesBinding
import com.example.otaku.anime.details.episodes.adapters.EpisodesAdapter
import com.example.otaku.anime.details.episodes.models.ContainerEpisodeHeader
import com.example.otaku.anime.details.episodes.models.ContainerEpisodes
import com.example.otaku.anime.details.episodes.models.DisplayableItem
import com.example.otaku.anime.details.info.ui.DetailsViewModel
import com.example.otaku.anime.details.info.ui.DetailsViewModelFactory
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject


class EpisodesFragment : Fragment() {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    private val args: EpisodesFragmentArgs by navArgs()

    @Inject
    lateinit var vmFactory: DetailsViewModelFactory


    private val episodesAdapter by lazy {
        EpisodesAdapter(
            actionSearch = { actionSearch(it) },
            onBackPressed = { requireActivity().onBackPressed() }
        )
    }

    private lateinit var dViewModel: DetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(layoutInflater)

        (requireActivity().applicationContext as App).appComponent.inject(this)
        dViewModel = ViewModelProvider(this, vmFactory)[DetailsViewModel::class.java]

        subscribesToFlow()

        (binding.root.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        initAdapter()

        episodesAdapter.submitList(getList(args.episodes))

        return binding.root
    }

    private fun initAdapter() = with(binding.root) {
        adapter = episodesAdapter
        layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun actionSearch(episode: Int) {
        dViewModel.getVideos(
            episode = episode,
            kind = args.kind,
            name = args.name,
            malId = args.malId
        )
    }

    private fun subscribesToFlow() = with(dViewModel) {

        subscribeToFlow(
            flow = actionVideo,
            lifecycleOwner = viewLifecycleOwner
        ) { list ->
            if (list.isNotEmpty()) {
                findNavController().navigate(
                    EpisodesFragmentDirections.actionEpisodesFragmentToWebPlayerFragment(list.first().url)
                )
            } else {
                BannerUtils.showToast(
                    binding.root, NOT_FOUND_TEXT, requireContext(), requireActivity().findViewById(
                        R.id.navBottom
                    )
                )
            }
        }
    }


    private fun getList(count: Int): List<DisplayableItem> {
        val list =
            mutableListOf<DisplayableItem>()

        list.add(ContainerEpisodeHeader {
            BannerUtils.showToast(
                binding.root,
                "Episodes: $count",
                requireContext()
            )
        })

        var i = 1
        while (list.count() <= count) {

            list.add(ContainerEpisodes(episode = i))
            i++
        }
        return list
    }


}