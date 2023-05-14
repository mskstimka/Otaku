package com.example.otaku.persons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.otaku.anime.details.info.adapters.characters.ContainerCharactersAdapter
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentPersonBinding
import com.example.otaku.persons.adapters.info.ContainerPersonInfoAdapter
import com.example.otaku.persons.adapters.roles.ContainerWorksAdapter
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject


class PersonFragment : Fragment() {

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    private val args: PersonFragmentArgs by navArgs()

    @Inject
    lateinit var pViewModel: PersonViewModel

    private val infoAdapter by lazy {
        ContainerPersonInfoAdapter(
            onBackPressed = { requireActivity().onBackPressed() },
            favoriteAction = { action -> pViewModel.actionFavorites(action) }
        )
    }
    private val worksAdapter by lazy {
        ContainerWorksAdapter {
            findNavController().navigate(
                PersonFragmentDirections.actionPersonFragmentToDetailsFragment(it)
            )
        }
    }
    private val characterAdapter by lazy {
        ContainerCharactersAdapter {
            findNavController().navigate(
                PersonFragmentDirections.actionPersonFragmentToCharactersFragment(it)
            )
        }
    }
    private val rootAdapter by lazy { MergeAdapter(infoAdapter, worksAdapter, characterAdapter) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().applicationContext as App).appComponent.inject(this)

        pViewModel.getPerson(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToFlows()
    }

    private fun subscribeToFlows() = with(pViewModel) {
        actionInfo.subscribeToFlow(
            lifecycleOwner = viewLifecycleOwner
        ) { personModel ->

            infoAdapter.submitList(personModel.person)

            if (personModel.works.first().list.isNotEmpty()) {
                worksAdapter.submitList(personModel.works)
            }

            if (personModel.characters.first().list.isNotEmpty()) {
                characterAdapter.submitList(personModel.characters)
            }
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