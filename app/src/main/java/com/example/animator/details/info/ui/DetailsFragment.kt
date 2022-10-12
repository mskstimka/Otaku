package com.example.animator.details.info.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.animator.R
import com.example.animator.app.App
import com.example.animator.databinding.FragmentDetailsBinding
import com.example.animator.details.info.adapters.persons.ContainerPerson
import com.example.animator.details.info.adapters.persons.ContainerPersonAdapter
import com.example.animator.details.info.adapters.characters.ContainerCharacters
import com.example.animator.details.info.adapters.characters.ContainerCharactersAdapter
import com.example.animator.details.info.adapters.details.ContainerDetailsAdapter
import com.example.animator.details.info.adapters.franchises.ContainerFranchisesAdapter
import com.example.animator.details.info.adapters.screenshots.ContainerScreenshotsAdapter
import com.example.animator.details.info.adapters.studios.ContainerStudios
import com.example.animator.details.info.adapters.studios.ContainerStudiosAdapter
import com.example.animator.details.info.adapters.videos.ContainerVideos
import com.example.animator.details.info.adapters.videos.ContainerVideosAdapter
import com.example.animator.details.info.services.StatusForegroundService
import com.example.animator.utils.BannerUtils
import com.example.animator.utils.subscribeToFlow
import com.example.animator_domain.*
import com.example.animator_domain.models.details.AnimeDetailsEntity
import javax.inject.Inject


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var vmFactory: DetailsViewModelFactory

    private var isWatch = false

    private lateinit var dViewModel: DetailsViewModel

    private val containerDetailsAdapter by lazy {
        ContainerDetailsAdapter { activity?.onBackPressed() }
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

    private var currentItem: AnimeDetailsEntity? = null
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().applicationContext as App).appComponent.inject(this)
        dViewModel = ViewModelProvider(this, vmFactory)[DetailsViewModel::class.java]

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

        subscribeToFlow(
            flow = pageAnimeDetailsAction,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            containerDetailsAdapter.submitList(listOf(item))
            containerVideosAdapter.submitList(
                listOf(
                    ContainerVideos(
                        list = item.videos
                    )
                )
            )
            containerStudiosAdapter.submitList(
                listOf(
                    ContainerStudios(
                        list = item.studios
                    )
                )
            )

            currentItem = item

            val statusIntent =
                Intent(requireActivity(), StatusForegroundService::class.java)

            statusIntent
                .putExtra(STATUS_FOREGROUND_ENGLISH_NAME_KEY, item.name)
                .putExtra(STATUS_FOREGROUND_RUSSIAN_NAME_KEY, item.russian)
                .putExtra(STATUS_FOREGROUND_KIND_KEY, item.kind)
            startForegroundService(requireContext(), statusIntent)

        }

        subscribeToFlow(
            flow = pageAnimeScreenshotsAction,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            containerScreenshotsAdapter.submitList(listOf(item))
        }


        subscribeToFlow(
            flow = pageAnimeFranchisesAction,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            containerFranchisesAdapter.submitList(listOf(item))
        }

        subscribeToFlow(
            flow = pageAnimeRolesAction,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->
            containerCharactersAdapter.submitList(
                listOf(
                    ContainerCharacters(
                        list = item.character
                    )
                )
            )
            containerPersonAdapter.submitList(
                listOf(
                    ContainerPerson(
                        list = item.person,
                        title = getString(R.string.fragment_details_tvTitleAutors_text)
                    )
                )
            )
        }

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
            flow = actionEpisodes,
            lifecycleOwner = viewLifecycleOwner
        ) { episodes ->
            if (episodes != 0) {
                binding.btWatch.apply {
                    visibility = View.VISIBLE
                    isWatch = true
                    setOnClickListener {
                        findNavController().navigate(
                            DetailsFragmentDirections.actionDetailsFragmentToEpisodesFragment(
                                episodes = episodes,
                                kind = currentItem?.kind!!,
                                name = currentItem?.name!!,
                                malId = currentItem?.id!!.toLong()
                            )
                        )
                    }
                }

            }

        }



        subscribeToFlow(
            flow = actionAdapter,
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