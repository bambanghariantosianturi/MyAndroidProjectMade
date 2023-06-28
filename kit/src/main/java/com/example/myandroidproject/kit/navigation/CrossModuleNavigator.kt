package com.example.myandroidproject.kit.navigation

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

interface CrossModuleNavigator {

    fun crossModuleActivityNavigateTo(
        activity: Activity,
        classTarget: KClass<*>?,
        bundle: Bundle? = null,
        requestCode: Int? = null
    )

    fun crossModuleFragmentNavigateTo(
        fragment: Fragment,
        classTarget: KClass<*>,
        bundle: Bundle,
        requestCode: Int
    )

    fun classDetailActivity(): KClass<*>
}