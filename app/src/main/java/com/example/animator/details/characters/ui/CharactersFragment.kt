package com.example.animator.details.characters.ui

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
import com.example.animator.app.App
import com.example.animator.databinding.FragmentCharactersBinding
import com.example.animator.details.characters.adapters.info.ContainerCharacterInfo
import com.example.animator.details.characters.adapters.info.ContainerCharacterInfoAdapter
import com.example.animator.details.info.adapters.franchises.ContainerFranchises
import com.example.animator.details.info.adapters.persons.ContainerPerson
import com.example.animator.details.info.adapters.persons.ContainerPersonAdapter
import com.example.animator.details.info.adapters.franchises.ContainerFranchisesAdapter
import com.example.animator.details.info.ui.DetailsFragmentDirections
import com.example.animator.details.persons.ui.PersonFragment
import com.example.animator.utils.BannerUtils
import com.example.animator.utils.subscribeToFlow
import javax.inject.Inject


class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val args: CharactersFragmentArgs by navArgs()

    private lateinit var cViewModel: CharactersViewModel


    private val infoAdapter by lazy {
        ContainerCharacterInfoAdapter {
            requireActivity().onBackPressed()
        }
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

    @Inject
    lateinit var vmFactory: CharactersViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)
        cViewModel = ViewModelProvider(this, vmFactory)[CharactersViewModel::class.java]

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
        subscribeToFlow(
            flow = actionInfo,
            lifecycleOwner = viewLifecycleOwner
        ) { item ->

            infoAdapter.submitList(listOf(ContainerCharacterInfo(item = item)))
            personAdapter.submitList(listOf(ContainerPerson(item.seyu, title = "Сейю")))
            franchisesAdapter.submitList(listOf(ContainerFranchises(list = item.animes)))

            binding.pbLoading.visibility = View.GONE
            initAdapters()
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
    }

    private fun initAdapters() = with(binding.rvRoot) {
        layoutManager = LinearLayoutManager(context)
        adapter = rootAdapter
    }
}