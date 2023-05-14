package com.example.otaku.app.activities.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.otaku.R
import com.example.otaku.agreement.UserAgreementFragmentDialog
import com.example.otaku.app.App
import com.example.otaku.databinding.ActivityMainBinding
import com.example.otaku.utils.ContextUtils
import com.example.otaku_data.utils.SharedPreferencesHelper
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

    override fun attachBaseContext(newBase: Context) {
        val isUkraineLanguage = SharedPreferencesHelper(newBase).getIsUkraineLanguage()

        val locale = if (isUkraineLanguage) {
            Locale("uk")
        } else {
            Locale("en")
        }
        val localeUpdatedContext = ContextUtils.updateLocale(newBase, locale)
        super.attachBaseContext(localeUpdatedContext)
    }


    private fun initNavigation() = with(binding.navBottom) {

        val navController =
            Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navBottom, navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            visibility = when (destination.id) {
                R.id.detailsFragment, R.id.episodesFragment, R.id.webPlayerFragment, R.id.charactersFragment, R.id.personFragment, R.id.screenshotsFragment, R.id.animeRatesFragment, R.id.userFragment -> View.GONE
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




