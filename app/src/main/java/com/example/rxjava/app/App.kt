package com.example.rxjava.app

import android.app.Application
import com.example.rxjava.di.AppComponent
import com.example.rxjava.di.AppModule
import com.example.rxjava.di.DaggerAppComponent
import dagger.Component

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }
}