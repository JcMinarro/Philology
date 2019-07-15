package com.jcminarro.philology

import android.content.res.Resources
import android.os.Build
import com.ibm.icu.text.PluralRules
import java.util.Locale

internal class ResourcesUtil(private val baseResources: Resources) {
    private val repository: PhilologyRepository by lazy {
        Philology.getPhilologyRepository(baseResources.currentLocale())
    }

    @Throws(Resources.NotFoundException::class)
    fun getText(id: Int): CharSequence {
        return repository.getText(Resource.Text(baseResources.getResourceEntryName(id)))
            ?: baseResources.getText(id)
    }

    @Throws(Resources.NotFoundException::class)
    fun getString(id: Int): String = getText(id).toString()

    @Throws(Resources.NotFoundException::class)
    fun getQuantityText(id: Int, quantity: Int): CharSequence = repository.getText(
        Resource.Plural(
            baseResources.getResourceEntryName(id),
            quantity.toPluralKeyword(baseResources.currentLocale())
        )
    ) ?: baseResources.getQuantityText(id, quantity)

    @Throws(Resources.NotFoundException::class)
    fun getQuantityString(id: Int, quantity: Int): String = getQuantityText(id, quantity).toString()

    @Throws(Resources.NotFoundException::class)
    fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String =
        String.format(getQuantityString(id, quantity), *formatArgs)
}

interface PhilologyRepository {
    fun getText(resource: Resource): CharSequence?
}

sealed class Resource(open val key: String) {
    data class Text(override val key: String) : Resource(key)
    data class Plural(override val key: String, val quantityKeyword: String) : Resource(key)
}

@SuppressWarnings("NewApi")
private fun Resources.currentLocale(): Locale = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
    @Suppress("DEPRECATION")
    configuration.locale
} else {
    configuration.locales[0]
}

private fun Int.toPluralKeyword(locale: Locale): String {
    return PluralRules.forLocale(locale).select(this.toDouble())
}