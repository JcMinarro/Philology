package com.jcminarro.philology

import android.content.res.Resources

internal class ResourcesUtil(private val baseResources: Resources) {

    @Throws(Resources.NotFoundException::class)
    fun getText(id: Int): CharSequence {
        return baseResources.getText(id)
    }

    @Throws(Resources.NotFoundException::class)
    fun getString(id: Int): String = getText(id).toString()
}

interface PhilologyRepository {
    fun getText(key: String): CharSequence?
}