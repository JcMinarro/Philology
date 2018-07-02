package com.jcminarro.philology

import android.content.res.Resources

@Suppress("DEPRECATION")
internal class PhilologyResources(baseResources: Resources)
    : Resources(baseResources.assets, baseResources.displayMetrics, baseResources.configuration) {
    private val resourcesUtil = ResourcesUtil(baseResources)

    override fun getText(id: Int): CharSequence = resourcesUtil.getText(id)
    override fun getString(id: Int): String = resourcesUtil.getString(id)
}