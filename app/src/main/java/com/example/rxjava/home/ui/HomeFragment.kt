package com.example.rxjava.home.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.rxjava.app.App
import com.example.rxjava.databinding.FragmentHomeBinding
import com.example.rxjava.home.adapters.*
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
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(layoutInflater)


        (requireActivity().applicationContext as App).appComponent.inject(this)


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        hViewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]
        subscribeToLiveData()





        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    private fun subscribeToLiveData() = with(hViewModel) {
        pageAnimePrevPosterAction.observe(this@HomeFragment) { item ->
            adapter.submitList(item)
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}