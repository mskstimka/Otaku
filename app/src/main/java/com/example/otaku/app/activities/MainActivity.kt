package com.example.otaku.app.activities

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.otaku.R
import com.example.otaku.app.App
import com.example.otaku.databinding.ActivityMainBinding
import com.example.otaku.utils.SharedPreferencesHelper
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComponent.inject(this)

        MobileAds.initialize(this) {}

        setLocale(sharedPreferencesHelper.getIsUkraineLanguage())

        initNavigation()
    }


    private fun setLocale(switch: Boolean) {
        val locale3 = if (switch) {
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
                R.id.detailsFragment, R.id.episodesFragment, R.id.webPlayerFragment, R.id.charactersFragment, R.id.personFragment -> View.GONE
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

}




