package com.jcminarro.philology

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import androidx.appcompat.widget.VectorEnabledTintResources

@SuppressWarnings("RestrictedApi")
@SuppressLint("RestrictedApi")
internal class PhilologyContextWrapper(base: Context) : ContextWrapper(base) {
    private val res: Resources by lazy {
        val resourcesUtil = ResourcesUtil(super.getResources())
        if (VectorEnabledTintResources.shouldBeUsed()) {
            PhilologyVectorEnabledTintResources(this, resourcesUtil)
        } else {
            PhilologyResources(resourcesUtil)
        }
    }

    override fun getResources(): Resources = res
}