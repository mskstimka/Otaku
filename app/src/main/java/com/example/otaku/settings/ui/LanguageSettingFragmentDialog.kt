package com.example.otaku.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.DialogSettingLanguageBinding
import com.example.otaku_data.utils.SharedPreferencesHelper
import javax.inject.Inject


class LanguageSettingFragmentDialog : DialogFragment() {

    private var _binding: DialogSettingLanguageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.DialogTheme_transparent)

        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSettingLanguageBinding.inflate(layoutInflater)

        bindView()

        return binding.root
    }


    private fun bindView() = with(binding) {

        binding.btComplete.setOnClickListener {
            dismiss()
        }

        sIsName.isChecked =
            sharedPreferencesHelper.getIsUkraineName()

        sIsDescription.isChecked =
            sharedPreferencesHelper.getIsUkraineDescription()

        sIsTitle.isChecked =
            sharedPreferencesHelper.getIsUkraineLanguage()

        sIsTitle.isClickable = false

        sIsName.setOnCheckedChangeListener { _, b ->
            sharedPreferencesHelper.setIsUkraineName(
                when (b) {
                    true -> true
                    else -> false
                }
            )
        }

        sIsDescription.setOnCheckedChangeListener { _, b ->
            sharedPreferencesHelper.setIsUkraineDescription(
                when (b) {
                    true -> true
                    else -> false
                }
            )
        }
    }

}