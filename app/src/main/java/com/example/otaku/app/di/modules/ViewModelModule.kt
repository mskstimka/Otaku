package com.example.otaku.app.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.otaku.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}