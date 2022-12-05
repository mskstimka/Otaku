package com.example.otaku.app.di.modules

import com.example.otaku.app.firebase.FirebaseMessagingServiceImpl
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFirebaseMessagingService(): FirebaseMessagingServiceImpl

}