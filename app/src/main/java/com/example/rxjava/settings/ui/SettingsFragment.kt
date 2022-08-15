package com.example.rxjava.settings.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.a16_rxjava_domain.Constants
import com.example.rxjava.R
import com.example.rxjava.databinding.ActivityMainBinding
import com.example.rxjava.databinding.FragmentDetailsBinding
import com.example.rxjava.databinding.FragmentSettingsBinding
import com.example.rxjava.utils.BannerUtils


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)

        val sharedPreferences = requireActivity().getSharedPreferences(
            Constants.SHARED_PREF_SETTINGS,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val isShowNotification = sharedPreferences.getBoolean(Constants.IS_SHOW_NOTIFICATION, true)

        binding.sNotification.isChecked = isShowNotification

        binding.sNotification.setOnCheckedChangeListener { _, b ->
            when (b){
                true -> {
                    editor.putBoolean(Constants.IS_SHOW_NOTIFICATION, true).apply()
                }
                else -> {
                    editor.putBoolean(Constants.IS_SHOW_NOTIFICATION, false).apply()
                }
            }
        }



        return binding.root
    }


}