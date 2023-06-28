package com.example.myandroidproject.favorite.component

import android.content.Context
import com.example.myandroidproject.di.FavoriteModuleDependencies
import com.example.myandroidproject.favorite.ui.FavoriteActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {

        fun context(@BindsInstance context: Context): Builder

        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder

        fun build(): FavoriteComponent
    }
}