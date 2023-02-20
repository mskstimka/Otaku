package com.example.otaku.app.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.domain.AD_ID_ON_BACK_PRESSED
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.ActivityMainBinding
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.otaku.agreement.UserAgreementFragmentDialog
import com.example.otaku.settings.ui.LanguageSettingFragmentDialog
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    private var mInterstitialAd: InterstitialAd? = null
    private var isAdLoaded = false

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComponent.inject(this)

        MobileAds.initialize(this) {}

        setLocale(sharedPreferencesHelper.getIsUkraineLanguage())

        loadAds()

        initNavigation()

        showUserAgreement()
    }

    private fun showUserAgreement() = with(binding) {
        val isShow = sharedPreferencesHelper.getIsShowUserAgreement()

        if (isShow) {
            val userAgreementDialog = UserAgreementFragmentDialog()

            userAgreementDialog.apply {
                isCancelable = false
                show(supportFragmentManager, "User Agreement")
            }
        }
    }


    private fun setLocale(switch: Boolean) {
        val titleCheck = sharedPreferencesHelper.getIsUkraineTitle()

        val locale3 = if (switch && titleCheck) {
            Locale("uk")
        } else {
            Locale("en")
        }
        Locale.setDefault(locale3)
        val config3 = Configuration()
        config3.locale = locale3
        baseContext.resources.updateConfiguration(config3, baseContext.resources.displayMetrics)

    }


    private fun initNavigation() = with(binding.navBottom) {

        val navController =
            Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navBottom, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            visibility = when (destination.id) {
                R.id.detailsFragment, R.id.episodesFragment, R.id.webPlayerFragment, R.id.charactersFragment, R.id.personFragment, R.id.screenshotsFragment -> View.GONE
                else -> View.VISIBLE
            }
            if (destination.id == R.id.webPlayerFragment) {
                window.decorView.apply {
                    systemUiVisibility =
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
                }
            } else {
                window.decorView.apply {
                    systemUiVisibility = View.VISIBLE
                }
            }
        }
    }

    // ca-app-pub-9350077428310070/5938417575
    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            AD_ID_ON_BACK_PRESSED,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("TAG", adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("TAG", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    isAdLoaded = true
                }
            })


        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d("TAG", "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d("TAG", "Ad dismissed fullscreen content.")
                mInterstitialAd = null
                onBackPressed()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e("TAG", "Ad failed to show fullscreen content.")
                mInterstitialAd = null
                onBackPressed()
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("TAG", "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("TAG", "Ad showed fullscreen content.")
            }

        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)

    }

    private fun showAds(actionError: () -> Unit) {
        if (isAdLoaded) {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            }
        } else {
            Log.d("Error", "ADS NOT FILL")
            loadAds()
            actionError()
        }
    }

    override fun onBackPressed() {

        when (findNavController(R.id.nav_host_fragment).currentDestination?.id) {
            R.id.episodesFragment, R.id.detailsFragment -> {
                showAds { super.onBackPressed() }
            }
            else -> super.onBackPressed()
        }
    }
}




