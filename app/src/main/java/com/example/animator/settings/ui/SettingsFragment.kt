package com.example.animator.settings.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.animator_domain.IS_SHOW_NOTIFICATION
import com.example.animator_domain.SHARED_PREF_SETTINGS
import com.example.animator.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)

        val sharedPreferences = requireActivity().getSharedPreferences(
            SHARED_PREF_SETTINGS,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val isShowNotification = sharedPreferences.getBoolean(IS_SHOW_NOTIFICATION, true)

        binding.sFragmentSettingsNotificationPush.isChecked = isShowNotification

        binding.sFragmentSettingsNotificationPush.setOnCheckedChangeListener { _, b ->
            when (b) {
                true -> {
                    editor.putBoolean(IS_SHOW_NOTIFICATION, true).apply()
                }
                else -> {
                    editor.putBoolean(IS_SHOW_NOTIFICATION, false).apply()
                }
            }
        }

        return binding.root
    }
}