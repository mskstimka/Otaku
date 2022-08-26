package com.example.animator.details.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.animator_domain.*
import com.example.animator.R
import com.example.animator.app.App
import com.example.animator.details.services.StatusForegroundService
import com.example.animator.utils.BannerUtils
import com.example.animator.databinding.FragmentDetailsBinding
import com.example.animator.details.adapters.roles.ContainerRoles
import com.example.animator.details.adapters.roles.characters.ContainerCharactersAdapter
import com.example.animator.details.adapters.details.ContainerDetailsAdapter
import com.example.animator.details.adapters.franchises.ContainerFranchises
import com.example.animator.details.adapters.franchises.ContainerFranchisesAdapter
import com.example.animator.details.adapters.roles.authors.ContainerAuthorsAdapter
import com.example.animator.details.adapters.screenshots.ContainerScreenshots
import com.example.animator.details.adapters.screenshots.ContainerScreenshotsAdapter
import com.example.animator.details.adapters.studios.ContainerStudios
import com.example.animator.details.adapters.studios.ContainerStudiosAdapter
import com.example.animator.details.adapters.videos.ContainerVideos
import com.example.animator.details.adapters.videos.ContainerVideosAdapter
import javax.inject.Inject


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var vmFactory: DetailsViewModelFactory

    private lateinit var dViewModel: DetailsViewModel

    private val containerScreenshotsAdapter by lazy { ContainerScreenshotsAdapter() }
    private val containerDetailsAdapter by lazy { ContainerDetailsAdapter { activity?.onBackPressed() } }
    private val containerVideosAdapter by lazy {
        ContainerVideosAdapter { intent ->
            startActivity(
                intent
            )
        }
    }
    private val containerCharactersAdapter by lazy { ContainerCharactersAdapter() }
    private val containerAuthorsAdapter by lazy { ContainerAuthorsAdapter() }
    private val containerFranchisesAdapter by lazy { ContainerFranchisesAdapter() }
    private val containerStudiosAdapter by lazy { ContainerStudiosAdapter() }

    private val rootAdapter =
        MergeAdapter(
            containerDetailsAdapter,
            containerScreenshotsAdapter,
            containerVideosAdapter,
            containerCharactersAdapter,
            containerAuthorsAdapter,
            containerFranchisesAdapter,
            containerStudiosAdapter
        )

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

        binding.rvRoot.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        binding.rvRoot.adapter = rootAdapter

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

    private fun getAnimeDetails(id: Int) {
        with(dViewModel) {
            getAnimeDetailsFromId(id = id)
            getAnimeDetailsScreenshotsFromId(id = id)
            getAnimeDetailsFranchisesFromId(id = id)
            getAnimeDetailsRolesFromId(id = id)
        }
    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()
    }

    @SuppressLint("ResourceType", "FragmentLiveDataObserve")
    private fun subscribeToLiveData() = with(dViewModel) {
        pageAnimeDetailsAction.observe(this@DetailsFragment) { item ->

            containerDetailsAdapter.submitList(listOf(item))
            containerVideosAdapter.submitList(listOf(ContainerVideos(item.videos)))
            containerStudiosAdapter.submitList(listOf(ContainerStudios(item.studios)))

            val statusIntent = Intent(requireActivity(), StatusForegroundService::class.java)

            statusIntent
                .putExtra(STATUS_FOREGROUND_ENGLISH_NAME_KEY, item.name)
                .putExtra(STATUS_FOREGROUND_RUSSIAN_NAME_KEY, item.russian)
                .putExtra(STATUS_FOREGROUND_KIND_KEY, item.kind)
            startForegroundService(requireContext(), statusIntent)

        }

        pageAnimeScreenshotsAction.observe(this@DetailsFragment) { item ->
            containerScreenshotsAdapter.submitList(listOf(ContainerScreenshots(list = item)))
        }

        pageAnimeFranchisesAction.observe(this@DetailsFragment) { item ->
            containerFranchisesAdapter.submitList(listOf(ContainerFranchises(item)))
        }

        pageAnimeRolesAction.observe(this@DetailsFragment) { item ->
            val list = listOf(ContainerRoles(item))
            containerCharactersAdapter.submitList(list)
            containerAuthorsAdapter.submitList(list)
        }
        actionError.observe(this@DetailsFragment) {
            BannerUtils.showToast(
                getString(R.string.an_error_has_occurred, it),
                requireContext()
            )
        }
    }

    companion object {
        fun newInstance(posterId: Int): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSTER_ID_KEY, posterId)
                }
            }
        }

        private const val POSTER_ID_KEY = "id"
        const val TAG_FRAGMENT = "details_fragment_tag"
        const val ZERO_TEXT = "0"
    }
}