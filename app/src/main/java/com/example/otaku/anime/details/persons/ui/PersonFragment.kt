package com.example.otaku.anime.details.persons.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentPersonBinding
import com.example.otaku.anime.details.info.adapters.characters.ContainerCharacters
import com.example.otaku.anime.details.info.adapters.characters.ContainerCharactersAdapter
import com.example.otaku.anime.details.persons.adapters.info.ContainerPersonInfo
import com.example.otaku.anime.details.persons.adapters.info.ContainerPersonInfoAdapter
import com.example.otaku.anime.details.persons.adapters.roles.ContainerWorks
import com.example.otaku.anime.details.persons.adapters.roles.ContainerWorksAdapter
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject


class PersonFragment : Fragment() {

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    private val args: PersonFragmentArgs by navArgs()

    @Inject
    lateinit var vmFactory: PersonViewModelFactory

    private lateinit var pViewModel: PersonViewModel

    private val infoAdapter by lazy { ContainerPersonInfoAdapter { requireActivity().onBackPressed() } }
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
        pViewModel = ViewModelProvider(this, vmFactory)[PersonViewModel::class.java]

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
        subscribeToFlow(
            flow = actionInfo,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->

            infoAdapter.submitList(listOf(ContainerPersonInfo(item = item)))

            if (item.works.isNotEmpty()) {
                worksAdapter.submitList(listOf(ContainerWorks(list = item.works)))
            }
            if (item.roles.isNotEmpty()) {
                characterAdapter.submitList(listOf(ContainerCharacters(item.roles.first().characters)))
            }
        }

        subscribeToFlow(
            flow = actionAdapter,
            lifecycleOwner = viewLifecycleOwner
        ) { visibility ->
            binding.pbLoading.visibility = visibility
            initAdapters()
        }

        subscribeToFlow(
            flow = actionError,
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