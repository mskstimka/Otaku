package com.example.otaku.characters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.otaku.anime.details.info.adapters.franchises.ContainerFranchisesAdapter
import com.example.otaku.anime.details.info.adapters.persons.ContainerPersonAdapter
import com.example.otaku.app.App
import com.example.otaku.characters.adapters.info.ContainerCharacterInfoAdapter
import com.example.otaku.databinding.FragmentCharactersBinding
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject


class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val args: CharactersFragmentArgs by navArgs()

    @Inject
    lateinit var cViewModel: CharactersViewModel


    private val infoAdapter by lazy {
        ContainerCharacterInfoAdapter(
            onBackPressed = { requireActivity().onBackPressed() },
            favoriteAction = { action -> cViewModel.actionFavorites(action) }
        )
    }
    private val personAdapter by lazy {
        ContainerPersonAdapter {
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToPersonFragment(it)
            )
        }
    }
    private val franchisesAdapter by lazy {
        ContainerFranchisesAdapter {
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
                    it
                )
            )
        }
    }

    private val rootAdapter by lazy { MergeAdapter(infoAdapter, personAdapter, franchisesAdapter) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)

        cViewModel.getCharacters(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToFlows()
    }

    private fun subscribeToFlows() = with(cViewModel) {
        actionInfo.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { characterModel ->

            infoAdapter.submitList(characterModel.character)
            personAdapter.submitList(characterModel.seyu)
            franchisesAdapter.submitList(characterModel.franchises)
        }

        actionProgressBar.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { visibility ->
            binding.pbLoading.visibility = visibility
            initAdapters()
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
    }

    private fun initAdapters() = with(binding.rvRoot) {
        layoutManager = LinearLayoutManager(context)
        adapter = rootAdapter
    }
}