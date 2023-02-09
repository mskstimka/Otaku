package com.example.otaku.settings.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.FragmentSettingsBinding
import com.example.otaku.utils.BannerUtils
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.otaku.utils.TranslateUtils
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

        sThemeList.apply {
            adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_text, resources.getStringArray(R.array.themes)
            )

            setSelection(sharedPreferencesHelper.getDayNightTheme(), false)

            onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?, position: Int, id: Long
                ) {
//                    when (position) {
//                        IS_DAY_THEME -> requireActivity().setTheme(R.style.AnimatorMaterial3_Day)
//                        IS_NIGHT_THEME -> requireActivity().setTheme(R.style.AnimatorMaterial3_Night)
//                        IS_AUTO_THEME -> requireActivity().setTheme(R.style.AnimatorMaterial3)
//                    }
                    sharedPreferencesHelper.setDayNightTheme(position)
//
                    BannerUtils.showSnackBar(
                        binding.root,
                        getString(R.string.theme_message),
                        requireContext(),
                        requireActivity().findViewById(R.id.navBottom)
                    )

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }


        }
        val settingsLanguage = LanguageSettingFragmentDialog()

        tvLanguageSettings.setOnClickListener {
            if (sFragmentSettingsUkraineLocale.isChecked) {
                settingsLanguage.show(requireActivity().supportFragmentManager, "Settings Language")
            }
        }

        sFragmentSettingsSearchCensored.isChecked =
            !sharedPreferencesHelper.getIsCensoredSearch()

        sFragmentSettingsNotificationPush.isChecked =
            sharedPreferencesHelper.getIsShowNotification()

        sFragmentSettingsUkraineLocale.isChecked =
            sharedPreferencesHelper.getIsUkraineLanguage()

        sFragmentSettingsSearchCensored.setOnCheckedChangeListener { _, b ->
            sharedPreferencesHelper.setIsCensoredSearch(
                when (b) {
                    true -> false
                    false -> true
                }
            )
        }
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
                        setLocale(it)
                        TranslateUtils.downloadLanguage()
                    }
                    else -> false.also { setLocale(it) }
                }
            )
        }
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