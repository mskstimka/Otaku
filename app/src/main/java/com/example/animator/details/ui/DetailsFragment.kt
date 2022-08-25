package com.example.animator.details.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startForegroundService
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animator_domain.*
import com.example.animator_domain.models.details.*
import com.example.animator.R
import com.example.animator.app.App
import com.example.animator.details.services.StatusForegroundService
import com.example.animator.utils.BannerUtils
import com.example.animator.databinding.FragmentDetailsBinding
import com.example.animator.details.adapters.*
import com.example.animator.utils.setImageByURL
import com.google.android.material.snackbar.Snackbar
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import javax.inject.Inject


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var vmFactory: DetailsViewModelFactory

    private lateinit var dViewModel: DetailsViewModel

    private val genresAdapter by lazy { GenresAdapter() }
    private val screenshotsAdapter by lazy { ScreenshotsAdapter() }
    private val videosAdapter by lazy {
        VideosAdapter { intent ->
            startActivity(intent)
        }
    }
    private val frachisesAdapter by lazy { FranchisesAdapter() }
    private val studiosAdapter by lazy { StudiosAdapter() }
    private val charactersAdapter by lazy { CharactersAdapter() }
    private val authorsAdapter by lazy { AuthorsAdapter() }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().applicationContext as App).appComponent.inject(this)
        dViewModel = ViewModelProvider(this, vmFactory)[DetailsViewModel::class.java]

        getAnimeDetails(args.id)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        initAdapters()

        binding.tvTitle.setOnClickListener {
            Snackbar.make(binding.root, binding.tvTitle.text, Snackbar.LENGTH_SHORT).show()
        }

        binding.ivBackPressed.setOnClickListener {
            activity?.onBackPressed()
        }

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)

        with(binding) {
            showSkeleton(
                tvEpisodeTitle, tvStatusTitle, tvDateTitle, tvGenreTitle, tvDescriptionTitle,
                tvTitleScreenshots, tvTitleVideos, tvTitleCharacters, tvTitleAutors,
                tvTitleFranchises, tvTitleStudios, ivImageFranchises, tvScore
            )
        }

        return binding.root
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
            initView(item = item)

            val statusIntent = Intent(requireActivity(), StatusForegroundService::class.java)

            statusIntent
                .putExtra(STATUS_FOREGROUND_ENGLISH_NAME_KEY, item.name)
                .putExtra(STATUS_FOREGROUND_RUSSIAN_NAME_KEY, item.russian)
                .putExtra(STATUS_FOREGROUND_KIND_KEY, item.kind)
            startForegroundService(requireContext(), statusIntent)

        }

        pageAnimeScreenshotsAction.observe(this@DetailsFragment) { item ->
            screenshotsAdapter.submitList(item)
        }

        pageAnimeFranchisesAction.observe(this@DetailsFragment) { item ->
            frachisesAdapter.submitList(item)
        }

        pageAnimeRolesAction.observe(this@DetailsFragment) { item ->
            charactersAdapter.submitList(item)
            authorsAdapter.submitList(item)
        }
        actionError.observe(this@DetailsFragment) {
            BannerUtils.showToast(
                getString(R.string.an_error_has_occurred, it),
                requireContext()
            )
        }
    }

    private fun initView(item: AnimeDetailsEntity) {
        with(binding) {

            ivImageFranchises.setImageByURL(SHIKIMORI_URL + item.image.original)
            ivImageBackground.setImageByURL(SHIKIMORI_URL + item.image.original)

            tvTitle.text = item.name
            tvTitleRussian.text = item.russian

            exDescription.text = if (item.description != null) {
                HtmlCompat.fromHtml(item.description_html, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                NOT_FOUND_TEXT
            }

            tvScore.text = item.score
            tvKind.text = item.kind

            tvEpisode.text = if (item.episodes.toString() != ZERO_TEXT) {
                item.episodes.toString()
            } else {
                item.episodes_aired.toString()
            }

            tvDate.text = item.aired_on
            tvStatus.text = item.status
            tvStatus.setTextColor(Color.parseColor(item.statusColor))

            genresAdapter.submitList(item.genres)
            videosAdapter.submitList(item.videos)
            studiosAdapter.submitList(item.studios)

            with(binding) {
                hideSkeleton(
                    tvEpisodeTitle, tvStatusTitle, tvDateTitle, tvGenreTitle, tvDescriptionTitle,
                    tvTitleScreenshots, tvTitleVideos, tvTitleCharacters, tvTitleAutors,
                    tvTitleFranchises, tvTitleStudios, ivImageFranchises, tvTitle, tvScore
                )
            }
        }
    }

    private fun initAdapters() = with(binding) {
        rvGenre.adapter = this@DetailsFragment.genresAdapter
        rvGenre.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rvScreenshots.adapter = this@DetailsFragment.screenshotsAdapter
        rvScreenshots.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rvVideos.adapter = this@DetailsFragment.videosAdapter
        rvVideos.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rvFranchises.adapter = this@DetailsFragment.frachisesAdapter
        rvFranchises.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rvCharacters.adapter = this@DetailsFragment.charactersAdapter
        rvCharacters.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rvAutors.adapter = this@DetailsFragment.authorsAdapter
        rvAutors.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rvStudios.adapter = this@DetailsFragment.studiosAdapter
        rvStudios.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    private fun showSkeleton(vararg view: View) = view.forEach { it.loadSkeleton() }

    private fun hideSkeleton(vararg view: View) = view.forEach { it.hideSkeleton() }

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