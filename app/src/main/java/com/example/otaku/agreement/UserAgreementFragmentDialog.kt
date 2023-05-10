package com.example.otaku.agreement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.DialogUserAgreementBinding
import javax.inject.Inject

class UserAgreementFragmentDialog : DialogFragment() {

    private var _binding: DialogUserAgreementBinding? = null
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
        _binding = DialogUserAgreementBinding.inflate(layoutInflater)

        initView()

        return binding.root
    }

    private fun initView(){
        binding.btComplete.setOnClickListener {
            sharedPreferencesHelper.setIsShowUserAgreement(false)
            dismiss()
        }
    }
}