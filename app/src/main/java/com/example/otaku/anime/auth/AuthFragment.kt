package com.example.otaku.anime.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.domain.models.user.UserBrief
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentAuthBinding
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.setImageByURL
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject


class AuthFragment : Fragment() {

    @Inject
    lateinit var aViewModel: AuthViewModel

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscribeToFlows()
    }

    private fun initView() = with(binding) {

        btAuth.setOnClickListener {
            with(aViewModel) {
                val userId = currentId
                val localToken = localToken
                if (isAuthorized && userId != null) {
                    findNavController().navigate(
                        AuthFragmentDirections.actionAuthFragmentToUserFragment(
                            userId
                        )
                    )
                } else if (localToken != null) {
                    aViewModel.getCurrentUser(localToken.authToken)
                } else {
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                }
            }
        }
    }


    private fun initSuccessView(user: UserBrief) = with(binding) {
        if (context != null) {
            tvCurrentUserInfo.text =
                getString(R.string.fragment_auth_tvCurrentUserInfo_success_text)
            ivUserAvatar.setImageByURL(user.image.x160 ?: "")
            tvTitle.text = user.nickname
            btAuth.text = getString(R.string.fragment_auth_btAuth_success_text)
            groupUserData.visibility = View.VISIBLE
        }
    }

    private fun initErrorView() = with(binding) {
        if (context != null) {

            btAuth.text = getString(R.string.fragment_auth_btAuth_error_text)
            tvCurrentUserInfo.text = getString(R.string.fragment_auth_tvCurrentUserInfo_error_text)
            groupUserData.visibility = View.VISIBLE
        }
    }

    private fun subscribeToFlows() = with(aViewModel) {

        actionError.subscribeToFlow(lifecycleOwner = requireActivity()) { message ->
            BannerUtils.showSnackBar(
                binding.root,
                message = message,
                requireContext()
            )
            initErrorView()
        }

        actionUserBrief.subscribeToFlow(lifecycleOwner = requireActivity()) { user ->
            initSuccessView(user)
        }
    }


}