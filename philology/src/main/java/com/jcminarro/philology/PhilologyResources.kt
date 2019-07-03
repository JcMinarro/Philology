package com.jcminarro.philology

import android.content.res.Resources

@Suppress("DEPRECATION")
internal class PhilologyResources(baseResources: Resources)
    : Resources(baseResources.assets, baseResources.displayMetrics, baseResources.configuration) {
    private val resourcesUtil = ResourcesUtil(baseResources)

    override fun getText(id: Int): CharSequence = resourcesUtil.getText(id)
    override fun getString(id: Int): String = resourcesUtil.getString(id)
    override fun getQuantityText(id: Int, quantity: Int): CharSequence {
        return resourcesUtil.getQuantityText(id, quantity)
    }
    override fun getQuantityString(id: Int, quantity: Int): String {
        return resourcesUtil.getQuantityString(id, quantity)
    }
    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        return resourcesUtil.getQuantityString(id, quantity, *formatArgs)
    }
}