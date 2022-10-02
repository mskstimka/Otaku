package com.example.animator.app.webPlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.animator.databinding.WebFragmentBinding


class WebPlayerFragment : Fragment() {

    private var _binding: WebFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: WebPlayerFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WebFragmentBinding.inflate(layoutInflater)

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        initWebView()

        return binding.root
    }

    private fun initWebView() = binding.webView.apply {
        webChromeClient = WebChromeClient()

        settings.apply {
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            javaScriptCanOpenWindowsAutomatically = true
            javaScriptEnabled = true
            domStorageEnabled = true
            allowContentAccess = true
            allowUniversalAccessFromFileURLs = true
            loadData(
                "<html><body style='margin:0;padding:0;'><iframe src='${args.url}' width='100%' height='100%'  frameborder='0' allowfullscreen></iframe></body></html>",
                "text/html",
                "UTF-8"
            )
        }
    }


    override fun onDestroy() {
        binding.webView.apply {
            webChromeClient = null
            destroy()
        }

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onDestroy()
    }


    override fun onStop() {
        super.onStop()

        binding.webView.stopLoading()
    }

}