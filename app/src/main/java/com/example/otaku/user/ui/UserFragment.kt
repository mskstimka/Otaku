package com.example.otaku.user.ui

import android.content.Intent
import android.content.LocusId
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.otaku.app.App
import com.example.otaku.app.activities.auth.AuthActivity
import com.example.otaku.databinding.FragmentUserBinding
import com.example.otaku.user.adapters.info.UserInfoAdapter
import com.example.otaku.user.adapters.stats.UserStatsAdapter
import com.example.otaku.user.adapters.stats.models.UserStatsContainer
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject
import kotlin.properties.Delegates


class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var uViewModel: UserViewModel

    private val userInfoAdapter by lazy { UserInfoAdapter() }
    var id by Delegates.notNull<Long>()
    private val userStatsAdapter by lazy { UserStatsAdapter(id) }

    private val rootAdapter by lazy { MergeAdapter(userInfoAdapter, userStatsAdapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)

        if (!uViewModel.isSavedLocalToken) {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToFlows()
    }

    private fun subscribeToFlows() = with(uViewModel) {

        actionError.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { message ->
            BannerUtils.showSnackBar(binding.root, message = message, requireContext())
        }

        actionUserBrief.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { user ->
            id = user.id
            initAdapters()
            userInfoAdapter.submitList(listOf(user))
        }

        actionUserStats.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { stats ->
            userStatsAdapter.submitList(mutableListOf(UserStatsContainer(stats)))
        }

        actionUserFavorites.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { stats ->

        }


    }

    private fun initAdapters() = with(binding.rvRoot) {
        layoutManager = LinearLayoutManager(context)
        adapter = rootAdapter
    }
}