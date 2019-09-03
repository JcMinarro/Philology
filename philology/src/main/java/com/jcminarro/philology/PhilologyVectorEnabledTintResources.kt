package com.jcminarro.philology

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import androidx.appcompat.widget.VectorEnabledTintResources

@SuppressWarnings("RestrictedApi")
@SuppressLint("RestrictedApi")
internal class PhilologyVectorEnabledTintResources(baseContext: Context, baseResources: Resources)
    : VectorEnabledTintResources(baseContext, baseResources) {
    private val resourcesUtil = ResourcesUtil(baseResources)

    override fun getText(id: Int): CharSequence = resourcesUtil.getText(id)
    override fun getString(id: Int): String = resourcesUtil.getString(id)
}