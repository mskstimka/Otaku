package com.example.animator.settings.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.animator.app.App
import com.example.animator.databinding.FragmentSettingsBinding
import com.example.animator.utils.SharedPreferencesHelper
import javax.inject.Inject


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)

        (requireActivity().applicationContext as App).appComponent.inject(this)


        binding.sFragmentSettingsNotificationPush.isChecked =
            sharedPreferencesHelper.getIsShowNotification()

        binding.sFragmentSettingsNotificationPush.setOnCheckedChangeListener { _, b ->
            sharedPreferencesHelper.setIsShowNotification(
                when (b) {
                    true -> true
                    else -> false
                }
            )
        }

        return binding.root
    }
}