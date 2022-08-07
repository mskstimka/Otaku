package com.example.rxjava.home.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.example.rxjava.R
import com.example.rxjava.app.App
import com.example.rxjava.app.utils.BannerUtils
import com.example.rxjava.databinding.FragmentHomeBinding
import com.example.rxjava.home.adapters.*
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
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(layoutInflater)


        (requireActivity().applicationContext as App).appComponent.inject(this)


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        hViewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]


        val skeletonScreen = Skeleton.bind(binding.recyclerView)
            .shimmer(true)// Следует ли открывать анимацию
            .angle(30)// угол наклона мерцанияран скелета, RecyclerView не может скользить, иначе он может скользить
            .duration(1200)// Время анимации в миллисекундах
            .load(R.layout.item_skeleton_news)// Скелет экрана UI
            .show();

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()



    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    private fun subscribeToLiveData() = with(hViewModel) {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pageAnimePosterAction.collect {
                    adapter.submitList(it)
                }

                actionError.collect {
                    BannerUtils.showToastError(
                        getString(R.string.an_error_has_occurred, it),
                        requireContext()
                    )
                }
            }
        }


    }

}