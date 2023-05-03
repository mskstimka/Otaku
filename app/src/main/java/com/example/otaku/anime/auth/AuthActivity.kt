package com.example.otaku.anime.auth

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.domain.SHIKIMORI_URL
import com.example.domain.repository.AuthRepository
import com.example.otaku.app.App
import com.example.otaku.databinding.ActivityAuthBinding
import com.example.otaku.utils.subscribeToFlow
import java.util.regex.Pattern
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!

    private val shikimoriClient by lazy { ShikimoriAuthClient() }

    @Inject
    lateinit var aViewModel: AuthViewModel

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initWebView()

        subscribeToFlows()
    }


    private fun subscribeToFlows() = with(aViewModel) {

        actionAuth.subscribeToFlow(lifecycleOwner = this@AuthActivity) {
            onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() = with(binding.webView) {
        loadUrl(SHIKIMORI_SIGN_IN_URL)
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webViewClient = shikimoriClient

    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private inner class ShikimoriAuthClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            interceptCode(url)
            return false
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            interceptCode(request?.url?.toString())
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if (url == SHIKIMORI_URL) {
                view?.loadUrl(AUTH_URL)
            }

        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        private fun interceptCode(url: String?) {
            Log.d("URL", url.toString())
            val matcher = Pattern.compile(SHIKIMORI_PATTERN).matcher(url)
            if (matcher.find()) {
                val authCode =
                    if (matcher.group().isNullOrEmpty()) ""
                    else url!!.substring(
                        url.lastIndexOf("/")
                    ).replaceFirst("/", "")

                aViewModel.signIn(authCode = authCode)

                binding.webView.isGone = true
            }
        }
    }

    companion object {
        const val AUTH_URL =
            "https://shikimori.me/oauth/authorize?client_id=f5zjBHcb3RpDSxABhUPb65L3vWNQc8cI4CF_k3LXR3o&redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&response_type=code&scope="

        private const val SHIKIMORI_PATTERN =
            "https?://(?:www\\.)?shikimori\\.me/oauth/authorize/(?:.*)"
        private const val SHIKIMORI_SIGN_UP_URL = "https://shikimori.one/users/sign_up"
        private const val SHIKIMORI_SIGN_IN_URL = "https://shikimori.one/users/sign_in"

    }
}


