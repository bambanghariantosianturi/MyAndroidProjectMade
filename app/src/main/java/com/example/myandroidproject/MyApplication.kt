package com.example.myandroidproject

import android.app.Application
import com.example.myandroidproject.kit.navigation.CrossModuleNavigator
import com.example.myandroidproject.kit.navigation.CrossModuleNavigatorProvider
import com.example.myandroidproject.router.CrossModuleRouter
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class MyApplication : Application(), CrossModuleNavigatorProvider {

    private lateinit var crossModuleNavigator: CrossModuleNavigator

    override fun onCreate() {
        super.onCreate()

        setUpCrossModuleNavigation()
    }

    private fun setUpCrossModuleNavigation() {
        crossModuleNavigator = CrossModuleRouter.getInstance()
    }

    override fun provideCrossModuleNavigator(): CrossModuleNavigator {
        return crossModuleNavigator
    }
}