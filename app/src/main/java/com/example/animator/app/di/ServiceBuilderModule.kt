package com.example.animator.app.di

import com.example.animator.app.firebase.FirebaseMessagingServiceImpl
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class ServiceBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFirebaseMessagingService(): FirebaseMessagingServiceImpl

}