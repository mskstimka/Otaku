package com.example.otaku.user.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentUserBinding
import com.example.otaku.user.adapters.favorites.UserFavoritesAdapter
import com.example.otaku.user.adapters.friends.UserFriendsAdapter
import com.example.otaku.user.adapters.history.UserHistoryAdapter
import com.example.otaku.user.adapters.info.UserInfoAdapter
import com.example.otaku.user.adapters.stats.UserStatsAdapter
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject


class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    val args: UserFragmentArgs by navArgs()

    @Inject
    lateinit var uViewModel: UserViewModel

    private val userInfoAdapter by lazy { UserInfoAdapter() { requireActivity().onBackPressed() } }
    private val userStatsAdapter by lazy { UserStatsAdapter(args.id) }
    private val userFavoritesAdapter by lazy { UserFavoritesAdapter() }
    private val userFriendsAdapter by lazy { UserFriendsAdapter() }
    private val userHistoryAdapter by lazy { UserHistoryAdapter() }

    private val rootAdapter by lazy {
        MergeAdapter(
            userInfoAdapter,
            userStatsAdapter,
            userHistoryAdapter,
            userFriendsAdapter,
            userFavoritesAdapter
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(layoutInflater)

        uViewModel.getUserInfo(args.id)
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
            userInfoAdapter.submitList(user)
        }

        actionUserStats.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { stats ->
            userStatsAdapter.submitList(stats)
        }

        actionUserFavorites.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { favoritesList ->
            if (favoritesList.first().all.isNotEmpty()) {
                userFavoritesAdapter.submitList(favoritesList)
            }
        }

        actionUserFriends.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { friendList ->
            if (friendList.first().list.isNotEmpty()) {
                userFriendsAdapter.submitList(friendList)
            }
        }

        progressBarAction.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { pbVisible ->
            binding.pbLoading.visibility = pbVisible
            initAdapters()
        }
        actionUserHistory.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { historyList ->
            if (historyList.isNotEmpty()) {
                userHistoryAdapter.submitList(historyList)
            }
        }
    }

    private fun initAdapters() = with(binding.rvRoot) {
        layoutManager = LinearLayoutManager(context)
        adapter = rootAdapter
    }
}