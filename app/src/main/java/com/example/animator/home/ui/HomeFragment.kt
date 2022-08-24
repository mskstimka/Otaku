package com.example.animator.home.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animator.R
import com.example.animator.app.App
import com.example.animator.databinding.FragmentHomeBinding
import com.example.animator.home.adapters.DisplayableAdapter
import com.example.animator.utils.BannerUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: HomeViewModelFactory

    private lateinit var hViewModel: HomeViewModel

    private val adapter: DisplayableAdapter by lazy { DisplayableAdapter() }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(layoutInflater)


        (requireActivity().applicationContext as App).appComponent.inject(this)


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        hViewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()


    }

    private fun subscribeToLiveData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                hViewModel.actionError.collect {
                    BannerUtils.showToast(
                        getString(R.string.an_error_has_occurred, it),
                        requireContext()
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                hViewModel.pageAnimePosterAction.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}