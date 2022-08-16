package com.example.rxjava.app.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.rxjava.app.LocalWorker
import com.example.rxjava.app.firebase.MyWorker
import com.example.rxjava.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController =
            Navigation.findNavController(this, com.example.rxjava.R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navBottom, navController)

        startWork()

    }



    private fun startWork() {

        val constraints: Constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(LocalWorker::class.java, 1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            "Local",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )

    }

}


