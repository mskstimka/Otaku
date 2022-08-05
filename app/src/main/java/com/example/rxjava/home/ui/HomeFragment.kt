package com.example.rxjava.home.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.telephony.gsm.GsmCellLocation
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_rxjava_domain.models.home.Advertising
import com.example.a16_rxjava_domain.models.home.PrevPoster
import com.example.a16_rxjava_domain.models.home.Title
import com.example.rxjava.app.App
import com.example.rxjava.databinding.FragmentHomeBinding
import com.example.rxjava.home.adapters.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: HomeViewModelFactory

    private lateinit var hViewModel: HomeViewModel

    private val prevPosterAdapter: PostAdapterDelegate by lazy { PostAdapterDelegate() }


//    private val items = listOf(
//        Title("Title 1"),
//        PrevPoster("Post 1"),
//        Advertising("Advertising: One"),
//        Title("Title 2"),
//        PrevPoster("Post 2"),
//        Advertising("Advertising : Two"),
//        Title("Title 3"),
//        PrevPoster("Post 3"),
//        Advertising("Advertising : Three")
//    )

    private val manager = AdapterDelegatesManager(
        TitleAdapterDelegate(),
        AdvertisingAdapterDelegate(),
        prevPosterAdapter
    )

    private val adapter = DelegationAdapter(manager)


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        (requireActivity().applicationContext as App).appComponent.inject(this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        hViewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]
        subscribeToLiveData()

        hViewModel.getAnimePrevPosterActionFromGenre(listOf(22))

        hViewModel.getAnimePrevPosterActionFromGenre(listOf(23))


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    private fun subscribeToLiveData() = with(hViewModel) {
        pageAnimePrevPosterAction.observe(this@HomeFragment) { item ->
            adapter.setItems(
                item
            )
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}