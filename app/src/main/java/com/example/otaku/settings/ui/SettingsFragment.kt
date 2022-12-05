package com.example.otaku.settings.ui

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentSettingsBinding
import com.example.otaku.utils.SharedPreferencesHelper
import com.example.otaku.utils.applyNewLocale
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*
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

        bindView()

        return binding.root
    }

    private fun bindView() = with(binding) {


        val settingsLanguage = LanguageSettingFragmentDialog()

        tvLanguageSettings.setOnClickListener {
            if (sFragmentSettingsUkraineLocale.isChecked) {
                settingsLanguage.show(requireActivity().supportFragmentManager, "Settings Language")
            }
        }
        sFragmentSettingsNotificationPush.isChecked =
            sharedPreferencesHelper.getIsShowNotification()


        sFragmentSettingsUkraineLocale.isChecked =
            sharedPreferencesHelper.getIsUkraineLanguage()


        sFragmentSettingsNotificationPush.setOnCheckedChangeListener { _, b ->
            sharedPreferencesHelper.setIsShowNotification(
                when (b) {
                    true -> true
                    else -> false
                }
            )
        }

        sFragmentSettingsUkraineLocale.setOnCheckedChangeListener { _, b ->
            sharedPreferencesHelper.setIsUkraineLanguage(
                when (b) {
                    true -> true.also {
                        requireContext().applyNewLocale(Locale("uk"))
                        downloadLanguage()
                    }
                    else -> false.also {  requireContext().applyNewLocale(Locale("en")) }
                }
            )
        }
    }



    private fun downloadLanguage() {
        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded(conditions)
    }

    private fun setLocale(switch: Boolean) {
        val titleCheck = sharedPreferencesHelper.getIsUkraineTitle()

        val locale3 = if (switch || titleCheck) {
            Locale("uk")
        } else {
            Locale("en")
        }
        Locale.setDefault(locale3)
        val config3 = Configuration()
        config3.locale = locale3
        requireActivity().baseContext.resources.updateConfiguration(
            config3,
            requireActivity().baseContext.resources.displayMetrics
        )


    }
}