package com.example.otaku.app.di.modules

import android.R
import android.app.Application
import android.content.Context
import android.widget.ArrayAdapter
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideContext(app: Application): Context = app


}