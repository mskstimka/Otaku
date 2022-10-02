package com.example.animator.app.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.animator.R
import com.example.animator.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        Navigation.findNavController(this, R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.webPlayerFragment) {
                binding.navBottom.visibility = View.GONE
            } else {
                binding.navBottom.visibility = View.VISIBLE
            }
        }
        val navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navBottom, navController)

    }


}


