package com.example.otaku.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentAuthBinding
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.setImageByURL
import com.example.otaku.utils.subscribeToFlow
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.models.user.UserBrief
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton


class AuthFragment : Fragment() {

    private lateinit var someActivityResultLauncher: ActivityResultLauncher<Intent>

    @Singleton
    @Inject
    lateinit var aViewModel: AuthViewModel

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)

        someActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                aViewModel.checkResult()
            }
        }
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

    override fun onResume() {
        super.onResume()

        initView()
    }

    private fun initView() = with(binding) {

        btSignOut.setOnClickListener {
            aViewModel.signOut()
        }

        ivSettings.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(SHIKIMORI_URL)
            }
            startActivity(intent)
        }

        btDevAccount.setOnClickListener {
            findNavController().navigate(
                AuthFragmentDirections.actionAuthFragmentToUserFragment(
                    577000
                )
            )
        }

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
                    someActivityResultLauncher.launch(
                        Intent(
                            requireContext(),
                            AuthActivity::class.java
                        )
                    )
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

    private fun initNoAuthView() = with(binding) {
        if (context != null) {

            ivUserAvatar.setImageResource(R.drawable.icon_default)
            btAuth.text = getString(R.string.fragment_auth_btAuth_error_text)
            tvCurrentUserInfo.text = getString(R.string.fragment_auth_tvCurrentUserInfo_error_text)
            groupUserData.visibility = View.VISIBLE
        }
    }

    private fun initErrorView() = with(binding) {
        if (context != null) {
            tvCurrentUserInfo.text = "Произошла ошибка"
            groupUserData.visibility = View.VISIBLE
        }
    }

    private fun subscribeToFlows() = with(aViewModel) {

        actionError.subscribeToFlow(lifecycleOwner = requireActivity()) { message ->
            if (context != null)
                BannerUtils.showSnackBar(
                    binding.root,
                    message = message,
                    requireContext()
                )
        }

        actionUserBrief.subscribeToFlow(lifecycleOwner = requireActivity()) { user ->
            initSuccessView(user)
        }

        actionAuth.subscribeToFlow(lifecycleOwner = requireActivity()) { action ->
            when (action) {
                AuthAction.IS_UNAUTHORIZED -> {
                    initNoAuthView()
                }
                is AuthAction.IS_AUTHORIZED -> {
                    aViewModel.getCurrentUser(accessToken = action.token.authToken)
                }
                is AuthAction.ACTIVITY_ON_BACK_PRESSED -> {
                    aViewModel.getCurrentUser(accessToken = action.token.authToken)
                }
                is AuthAction.ERROR -> {
                    initErrorView()
                    aViewModel.clearErrors()
                }
            }
        }
    }


}