package com.example.otaku.user.rates.anime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentAnimeRatesBinding
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import com.example.otaku_domain.models.user.status.RateStatus
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AnimeRatesFragment : Fragment() {

    private var _binding: FragmentAnimeRatesBinding? = null
    private val binding get() = _binding!!

    private val postersAdapter by lazy { AnimeRatesAdapter() }
    private val headerAdapter by lazy { HeaderUserRatesAdapter { requireActivity().onBackPressed() } }

    private val args: AnimeRatesFragmentArgs by navArgs()

    private val mergeAdapter = ConcatAdapter(headerAdapter, postersAdapter)

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
        aViewModel.getList(id = args.id, status = args.rateStatus)
    }

    private fun subscribeToFlows() = with(aViewModel) {

        actionError.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { message ->
            BannerUtils.showSnackBar(binding.root, message = message, requireContext())
            Log.d("Message", message)
        }

        actionPosters.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { list ->

            if (list != null) {
                lifecycleScope.launch {
                    postersAdapter.submitData(list)
                }
            }
        }
    }

    private fun initAdapter() = with(binding.rvRoot) {
        headerAdapter.submitList(
            listOf(
                HeaderUserRates(
                    headerText = args.rateStatus.replaceFirstChar(Char::titlecase)
                )
            )
        )
        adapter = mergeAdapter
        layoutManager = LinearLayoutManager(context)
    }


}