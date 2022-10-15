package com.example.otaku.details.episodes.ui

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentEpisodesBinding
import com.example.otaku.details.episodes.adapters.EpisodesAdapter
import com.example.otaku.details.episodes.models.ContainerEpisodeHeader
import com.example.otaku.details.episodes.models.ContainerEpisodes
import com.example.otaku.details.episodes.models.DisplayableItem
import com.example.otaku.details.info.ui.DetailsViewModel
import com.example.otaku.details.info.ui.DetailsViewModelFactory
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import com.example.animator_domain.NOT_FOUND_TEXT
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import javax.inject.Inject


class EpisodesFragment : Fragment() {
    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    private val args: EpisodesFragmentArgs by navArgs()

    @Inject
    lateinit var vmFactory: DetailsViewModelFactory

    private var mInterstitialAd: InterstitialAd? = null
    private var isAdLoaded = false

    private val episodesAdapter by lazy {
        EpisodesAdapter(
            actionSearch = { actionSearch(it) },
            onBackPressed = { showAds() }
        )
    }

    private lateinit var dViewModel: DetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAds()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(layoutInflater)

        (requireActivity().applicationContext as App).appComponent.inject(this)
        dViewModel = ViewModelProvider(this, vmFactory)[DetailsViewModel::class.java]

        Log.d("CREATE", "--------------------------")
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("CREATED", "--------------------------")

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
                BannerUtils.showToast(NOT_FOUND_TEXT, requireContext())
            }
        }
    }

    private fun loadAds() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-9350077428310070/5938417575",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("TAG", adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("TAG", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    isAdLoaded = true
                }
            })


        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d("TAG", "Ad dismissed fullscreen content.")
                mInterstitialAd = null
                requireActivity().onBackPressed()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                mInterstitialAd = null
            }

        }
    }

    private fun showAds() {
        if (isAdLoaded) {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            }
        } else {
            requireActivity().onBackPressed()
        }
    }


    private fun getList(count: Int): List<DisplayableItem> {
        val list =
            mutableListOf<DisplayableItem>()

        list.add(ContainerEpisodeHeader {
            BannerUtils.showToast(
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