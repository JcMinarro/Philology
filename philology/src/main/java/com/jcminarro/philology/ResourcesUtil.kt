package com.jcminarro.philology

import android.content.res.Resources
import android.icu.text.PluralRules
import android.os.Build
import java.util.Locale

internal class ResourcesUtil(private val baseResources: Resources) {
    private val repository: PhilologyRepository by lazy {
        Philology.getPhilologyRepository(baseResources.currentLocale())
    }

    @Throws(Resources.NotFoundException::class)
    fun getText(id: Int): CharSequence {
        return repository.getText(baseResources.getResourceEntryName(id))
            ?: baseResources.getText(id)
    }

    @Throws(Resources.NotFoundException::class)
    fun getString(id: Int): String = getText(id).toString()

    @Throws(Resources.NotFoundException::class)
    fun getQuantityText(id: Int, quantity: Int): CharSequence = repository.getPlural(
        baseResources.getResourceEntryName(id),
        quantity.toPluralKeyword(baseResources)
    ) ?: baseResources.getQuantityText(id, quantity)

    @Throws(Resources.NotFoundException::class)
    fun getQuantityString(id: Int, quantity: Int): String = getQuantityText(id, quantity).toString()

    @Throws(Resources.NotFoundException::class)
    fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String =
        String.format(getQuantityString(id, quantity), *formatArgs)

    fun getStringArray(id: Int): Array<String> =
        getTextArray(id).map { it.toString() }.toTypedArray()

    fun getTextArray(id: Int): Array<CharSequence> {
        return repository.getTextArray(baseResources.getResourceEntryName(id))
            ?: baseResources.getTextArray(id)
    }
}

interface PhilologyRepository {
    fun getText(key: String): CharSequence? = null
    fun getPlural(key: String, quantityString: String): CharSequence? = null
    fun getTextArray(key: String): Array<CharSequence>? = null
}

@SuppressWarnings("NewApi")
private fun Resources.currentLocale(): Locale = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
    @Suppress("DEPRECATION")
    configuration.locale
} else {
    configuration.locales[0]
}

private fun Int.toPluralKeyword(baseResources: Resources): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    PluralRules.forLocale(baseResources.currentLocale()).select(this.toDouble())
} else {
    baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, this)
}
