package com.example.myandroidproject.kit

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.myandroidproject.kit.navigation.CrossModuleNavigator
import com.example.myandroidproject.kit.navigation.CrossModuleNavigatorProvider
import kotlin.reflect.KClass

fun Activity.crossModuleNavigateTo(
    classTarget: KClass<*>?,
    bundle: Bundle? = null,
    requestCode: Int? = null
) {
    getCrossModuleNavigator().crossModuleActivityNavigateTo(this, classTarget, bundle, requestCode)
}

fun Activity.getCrossModuleNavigator(): CrossModuleNavigator {
    return (applicationContext as CrossModuleNavigatorProvider).provideCrossModuleNavigator()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}