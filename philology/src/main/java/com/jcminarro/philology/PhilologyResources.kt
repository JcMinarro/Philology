package com.jcminarro.philology

import android.content.res.Resources

@Suppress("DEPRECATION")
internal class PhilologyResources(baseResources: Resources) :
    Resources(baseResources.assets, baseResources.displayMetrics, baseResources.configuration) {
    private val resourcesUtil = ResourcesUtil(baseResources)

    override fun getText(id: Int): CharSequence = resourcesUtil.getText(id)

    override fun getText(id: Int, def: CharSequence): CharSequence = try {
        getText(id)
    } catch (_: NotFoundException) {
        def
    }

    override fun getString(id: Int): String = resourcesUtil.getString(id)

    override fun getQuantityText(id: Int, quantity: Int): CharSequence =
        resourcesUtil.getQuantityText(id, quantity)

    override fun getQuantityString(id: Int, quantity: Int): String =
        resourcesUtil.getQuantityString(id, quantity)

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String =
        resourcesUtil.getQuantityString(id, quantity, *formatArgs)

    override fun getStringArray(id: Int): Array<String> = resourcesUtil.getStringArray(id)

    override fun getTextArray(id: Int): Array<CharSequence> = resourcesUtil.getTextArray(id)
}
