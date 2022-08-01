package com.example.rxjava.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.rxjava.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
    }
}