package com.example.rxjava.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getColor
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_rxjava_domain.models.Constants
import com.example.a16_rxjava_domain.models.details.AnimeDetailsEntity
import com.example.rxjava.R
import com.example.rxjava.app.App
import com.example.rxjava.databinding.FragmentDetailsBinding
import com.example.rxjava.ui.adapters.*
import com.example.rxjava.viewmodels.details.DetailsViewModel
import com.example.rxjava.viewmodels.details.DetailsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import javax.inject.Inject


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var vmFactory: DetailsViewModelFactory

    private lateinit var dViewModel: DetailsViewModel

    private val genresAdapter by lazy { GenresAdapter(requireContext()) }
    private val screenshotsAdapter by lazy { ScreenshotsAdapter(requireContext()) }
    private val videosAdapter by lazy { VideosAdapter(requireContext()) }
    private val frachisesAdapter by lazy { FranchisesAdapter(requireContext()) }
    private val charactersAdapter by lazy { CharactersAdapter(requireContext()) }
    private val autorsAdapter by lazy { AutorsAdapter(requireContext()) }
    private val studiosAdapter by lazy { StudiosAdapter(requireContext()) }


    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().applicationContext as App).appComponent.inject(this)

        dViewModel = ViewModelProvider(this, vmFactory)[DetailsViewModel::class.java]

        subscribeToLiveData()
        getAnimeDetails(args.id)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()

        binding.tvTitle.setOnClickListener {
            Snackbar.make(binding.root, binding.tvTitle.text, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)

        binding.svScrollRoot.post {
            binding.svScrollRoot.fullScroll(ScrollView.FOCUS_UP)
        }
        return binding.root
    }

    private fun getAnimeDetails(id: Int) {
        with(dViewModel) {
            getAnimeDetailsFromId(id)
            getAnimeDetailsScreenshotsFromId(id)
            getAnimeDetailsFranchisesFromId(id)
            getAnimeDetailsRolesFromId(id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        subscribeToLiveData()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    private fun subscribeToLiveData() = with(dViewModel) {
        pageAnimeDetailsAction.observe(this@DetailsFragment) { item ->
            bindViewsDetailsPage(item)
        }

        pageAnimeScreenshotsAction.observe(this@DetailsFragment) { item ->
            screenshotsAdapter.submitList(item)
        }

        pageAnimeFranchisesAction.observe(this@DetailsFragment) { item ->
            frachisesAdapter.submitList(item)
        }

        pageAnimeRolesAction.observe(this@DetailsFragment) { item ->

            charactersAdapter.submitList(item)
            autorsAdapter.submitList(item)
        }
        actionError.observe(this@DetailsFragment) {
            Toast.makeText(
                context,
                getString(R.string.an_error_has_occurred, it),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun bindViewsDetailsPage(item: AnimeDetailsEntity) {
        with(binding) {
            Picasso.get().load(Constants.SHIKIMORI_URL + item.image.original)
                .into(ivImageFranchises)
            Picasso.get().load(Constants.SHIKIMORI_URL + item.image.original)
                .into(ivImageBackground)

            tvTitle.text = item.name
            tvTitleRussian.text = item.russian

            exDescription.text = if (item.description != null) {
                HtmlCompat.fromHtml(item.description_html, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                requireContext().getString(R.string.not_found)
            }

            tvScore.text = item.score
            tvKind.text = item.kind

            tvEpisode.text = if (item.episodes.toString() != "0") {
                item.episodes.toString()
            } else {
                item.episodes_aired.toString()
            }

            tvDate.text = item.aired_on
            tvStatus.text = item.status

            when (item.status) {
                Constants.ONGOING_STATUS -> tvStatus.setTextColor(getColor(context!!, R.color.blue_status))
                Constants.ANONS_STATUS -> tvStatus.setTextColor(getColor(context!!, R.color.red_status))
                Constants.RELEASED_STATUS -> tvStatus.setTextColor(getColor(context!!, R.color.green_status))
            }

            genresAdapter.submitList(item.genres)
            videosAdapter.submitList(item.videos)
            studiosAdapter.submitList(item.studios)
        }
    }


    private fun setAdapters() = with(binding) {
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

        rvAutors.adapter = this@DetailsFragment.autorsAdapter
        rvAutors.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rvStudios.adapter = this@DetailsFragment.studiosAdapter
        rvStudios.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}