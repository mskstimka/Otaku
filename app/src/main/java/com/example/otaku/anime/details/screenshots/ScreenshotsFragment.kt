package com.example.otaku.anime.details.screenshots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.otaku.databinding.FragmentScreenshotsBinding
import com.example.otaku.utils.setImageByURL


class ScreenshotsFragment : Fragment() {

    private var _binding: FragmentScreenshotsBinding? = null
    private val binding get() = _binding!!

    private val args: ScreenshotsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenshotsBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        pvPhoto.setImageByURL(args.photo)

        ivBackPressed.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}