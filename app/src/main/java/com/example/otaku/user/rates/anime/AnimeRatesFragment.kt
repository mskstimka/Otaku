package com.example.otaku.user.rates.anime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentAnimeRatesBinding
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject

class AnimeRatesFragment : Fragment() {

    private var _binding: FragmentAnimeRatesBinding? = null
    private val binding get() = _binding!!

    private val rootAdapter by lazy { AnimeRatesAdapter() }

    private val args: AnimeRatesFragmentArgs by navArgs()

    @Inject
    lateinit var aViewModel: AnimeRatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeRatesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        subscribeToFlows()
        aViewModel.getUserAnimeRates(id = args.id, page = 1, limit = 50, status = args.rateStatus)
    }

    private fun subscribeToFlows() = with(aViewModel) {

        actionError.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { message ->
            BannerUtils.showSnackBar(binding.root, message = message, requireContext())
            Log.d("Message", message)
        }

        actionPosters.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { list ->
            rootAdapter.submitList(list)
        }
    }

    private fun initAdapter() = with(binding.rvRoot) {
        adapter = rootAdapter
        layoutManager = LinearLayoutManager(context)
    }


}