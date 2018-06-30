package com.jcminarro.philology

import android.content.res.Resources
import android.os.Build
import java.util.Locale

internal class ResourcesUtil(private val baseResources: Resources) {
    private val repository: PhilologyRepository by lazy {
        Philology.getPhilologyRepository(baseResources.currentLocale())
    }

    @Throws(Resources.NotFoundException::class)
    fun getText(id: Int): CharSequence {
        return repository.getText(baseResources.getResourceEntryName(id)) ?: baseResources.getText(id)
    }

    @Throws(Resources.NotFoundException::class)
    fun getString(id: Int): String = getText(id).toString()
}

interface PhilologyRepository {
    fun getText(key: String): CharSequence?
}

private fun Resources.currentLocale(): Locale = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
    configuration.locale
} else {
    configuration.locales[0]
}