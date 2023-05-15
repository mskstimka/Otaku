package com.example.otaku.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentSearchBinding
import com.example.otaku.search.adapters.PostersAdapter
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PostersAdapter { binding.rvFragmentSearchList.scrollToPosition(0) } }

    @Inject
    lateinit var sViewModel: SearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        bindView()
        subscribeToFlow()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setAdapter() = with(binding) {
        rvFragmentSearchList.adapter = this@SearchFragment.adapter
        rvFragmentSearchList.layoutManager = LinearLayoutManager(context)
    }

    private fun hideIcon(visibility: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300)
            binding.gifFragmentSearchLogo.visibility = visibility
        }
    }

    private fun bindView() = with(binding) {


        svFragmentSearchSearch.setOnClickListener {
            svFragmentSearchSearch.onActionViewExpanded()
        }

        svFragmentSearchSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == "") {
                    hideIcon(View.VISIBLE)
                } else {
                    hideIcon(View.GONE)
                }
                sViewModel.onTextChanged(p0.toString())
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun subscribeToFlow() = with(sViewModel) {
        actionSearch.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) {

            lifecycleScope.launch {
                adapter.setData(it)
                delay(200)
            }

        }

        actionMessage.subscribeToFlow(lifecycleOwner = viewLifecycleOwner)
        { message ->
            BannerUtils.showSnackBar(
                binding.root,
                message,
                requireContext(),
                requireActivity().findViewById(
                    R.id.navBottom
                )
            )
        }
    }

}


