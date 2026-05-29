package com.example.magnoliaapp

import android.app.Application
import com.example.magnoliaapp.data.AppContainer
import com.example.magnoliaapp.data.DefaultAppContainer

class MagnoliasApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}