package com.example.otaku.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.models.user.UserBrief
import com.example.otaku.app.App
import com.example.otaku.app.activities.auth.AuthActivity
import com.example.otaku.databinding.FragmentUserBinding
import com.example.otaku.utils.BannerUtils
import com.example.otaku.utils.setImageByURL
import com.example.otaku.utils.subscribeToFlow
import javax.inject.Inject


class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var uViewModel: UserViewModel


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

        uViewModel.getCurrentUser()
    }

    private fun subscribeToFlows() = with(uViewModel) {

        actionError.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { message ->
            BannerUtils.showSnackBar(binding.root, message = message, requireContext())
        }

        actionUserBrief.subscribeToFlow(lifecycleOwner = viewLifecycleOwner) { user ->
            bindView(user = user)
        }
    }

    private fun bindView(user: UserBrief) = with(binding) {
        nameTextView.text = user.name
        nicknameTextView.text = user.nickname
        avatarImageView.setImageByURL(user.image.x160 ?: "null")
    }
}