package com.example.myandroidproject.router

import android.app.Activity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.myandroidproject.detail.ui.DetailActivity
import com.example.myandroidproject.kit.navigation.CrossModuleNavigator
import kotlin.reflect.KClass

class CrossModuleRouter : CrossModuleNavigator {

    companion object {
        private var instance: CrossModuleRouter? = null

        fun getInstance(): CrossModuleRouter {
            if (instance == null) {
                instance = CrossModuleRouter()
            }
            return instance as CrossModuleRouter
        }
    }

    override fun crossModuleActivityNavigateTo(
        activity: Activity,
        classTarget: KClass<*>?,
        bundle: Bundle?,
        requestCode: Int?
    ) {
        when (classTarget) {
            DetailActivity::class -> {
                DetailActivity.startActivity(activity, bundle ?: bundleOf())
            }
        }
    }

    override fun crossModuleFragmentNavigateTo(
        fragment: Fragment,
        classTarget: KClass<*>,
        bundle: Bundle,
        requestCode: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun classDetailActivity(): KClass<*> {
        return DetailActivity::class
    }
}