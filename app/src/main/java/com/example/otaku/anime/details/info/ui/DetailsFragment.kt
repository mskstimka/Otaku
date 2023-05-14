package com.example.otaku.anime.details.info.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.otaku.anime.details.info.adapters.characters.ContainerCharactersAdapter
import com.example.otaku.anime.details.info.adapters.details.ContainerDetailsAdapter
import com.example.otaku.anime.details.info.adapters.franchises.ContainerFranchisesAdapter
import com.example.otaku.anime.details.info.adapters.persons.ContainerPersonAdapter
import com.example.otaku.anime.details.info.adapters.screenshots.ContainerScreenshotsAdapter
import com.example.otaku.anime.details.info.adapters.studios.ContainerStudiosAdapter
import com.example.otaku.anime.details.info.adapters.videos.ContainerVideosAdapter
import com.example.otaku.anime.details.info.services.StatusForegroundService
import com.example.otaku.anime.details.info.ui.viewmodel.DetailsViewModel
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentDetailsBinding
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import com.example.otaku_domain.STATUS_FOREGROUND_ENGLISH_NAME_KEY
import com.example.otaku_domain.STATUS_FOREGROUND_KIND_KEY
import com.example.otaku_domain.STATUS_FOREGROUND_RUSSIAN_NAME_KEY
import javax.inject.Inject


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var dViewModel: DetailsViewModel


    private var isWatch = false

    private val containerDetailsAdapter by lazy {
        ContainerDetailsAdapter(
            onBackPressed = { activity?.onBackPressed() },
            updateOrCreateUserRate = { userRate -> dViewModel.updateOrCreateUserRate(userRate = userRate) },
            deleteUserRate = { id -> dViewModel.deleteUserRate(id = id) },
            favoriteAction = { action -> dViewModel.actionFavorites(action) }
        )
    }

    private val containerVideosAdapter by lazy {
        ContainerVideosAdapter { intent ->
            startActivity(
                intent
            )
        }
    }
    private val containerScreenshotsAdapter by lazy { ContainerScreenshotsAdapter() }
    private val containerCharactersAdapter by lazy {
        ContainerCharactersAdapter {
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToCharactersFragment(it)
            )
        }
    }
    private val containerPersonAdapter by lazy {
        ContainerPersonAdapter {
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToPersonFragment(it)
            )
        }
    }
    private val containerFranchisesAdapter by lazy {
        ContainerFranchisesAdapter {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentSelf(it))
        }
    }
    private val containerStudiosAdapter by lazy { ContainerStudiosAdapter() }

    private val listAdapters = listOf(
        containerDetailsAdapter,
        containerScreenshotsAdapter,
        containerVideosAdapter,
        containerCharactersAdapter,
        containerPersonAdapter,
        containerFranchisesAdapter,
        containerStudiosAdapter
    )
    private val rootAdapter = MergeAdapter(listAdapters)

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().applicationContext as App).appComponent.inject(this)

        getAnimeDetails(args.id)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)

        (binding.rvRoot.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        initRecycler()

        return binding.root
    }

    private fun initRecycler() = with(binding.rvRoot) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    checkVisibility(View.GONE)
                } else {
                    checkVisibility(View.VISIBLE)
                }
            }

        })
        layoutManager = LinearLayoutManager(context)

    }

    private fun checkVisibility(state: Int) {
        if (isWatch) {
            binding.btWatch.visibility = state
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribesToFlow()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().applicationContext.stopService(
            Intent(
                requireActivity(),
                StatusForegroundService::class.java
            )
        )
    }

    private fun getAnimeDetails(id: Int) = with(dViewModel) {
        getAnimeDetailsFromId(id = id)
        getAnimeDetailsScreenshotsFromId(id = id)
        getAnimeDetailsFranchisesFromId(id = id)
        getAnimeDetailsRolesFromId(id = id)
    }


    private fun subscribesToFlow() = with(dViewModel) {

        actionDetails.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { detailsModel ->

            with(detailsModel) {
                containerDetailsAdapter.submitList(details)
                containerVideosAdapter.submitList(videos)
                containerStudiosAdapter.submitList(studios)


                val statusIntent =
                    Intent(requireActivity(), StatusForegroundService::class.java)

                statusIntent
                    .putExtra(STATUS_FOREGROUND_ENGLISH_NAME_KEY, details.first().name)
                    .putExtra(STATUS_FOREGROUND_RUSSIAN_NAME_KEY, details.first().russian)
                    .putExtra(STATUS_FOREGROUND_KIND_KEY, details.first().kind)
                startForegroundService(requireContext(), statusIntent)
            }
        }

        actionScreenshots.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { screenshots ->
            containerScreenshotsAdapter.submitList(screenshots)
        }

        actionFranchises.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { franchises ->
            containerFranchisesAdapter.submitList(franchises)
        }

        pageAnimeRolesAction.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { roles ->
            containerCharactersAdapter.submitList(roles.characters)
            containerPersonAdapter.submitList(roles.persons)
        }

        actionMessage.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { message ->
            BannerUtils.showSnackBar(
                binding.root,
                message,
                requireContext()
            )
        }


        actionEpisodes.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { episodeModel ->
            if (episodeModel.episodes != 0) {
                binding.btWatch.apply {
                    visibility = View.VISIBLE
                    isWatch = true
                    setOnClickListener {
                        findNavController().navigate(
                            DetailsFragmentDirections.actionDetailsFragmentToEpisodesFragment(
                                episodes = episodeModel.episodes,
                                kind = episodeModel.kind,
                                name = episodeModel.name,
                                malId = episodeModel.id,
                                watched = episodeModel.episodesWatched
                            )
                        )
                    }
                }

            }

            actionUserRate.subscribeToFlow(
                lifecycleOwner = viewLifecycleOwner
            ) { list ->
                Log.d("RECIEVE", "-------------------")

                containerDetailsAdapter.submitList(list)

                BannerUtils.showSnackBar(
                    binding.root,
                    "User Rate Updated!",
                    requireContext()
                )
            }

        }


        actionProgressBar.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { visibility ->
            with(binding) {
                pbLoading.visibility = visibility
                rvRoot.adapter = rootAdapter
            }
        }
    }


    companion object {
        const val ZERO_TEXT = "0"
    }
}