package com.example.rxjava.app

import android.app.Application
import com.example.rxjava.app.di.AppComponent
import com.example.rxjava.app.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }
}