package com.jcminarro.philology

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.os.LocaleList
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import java.util.Locale

fun createConfiguration(locale: Locale = Locale.ENGLISH): Configuration = mock<Configuration>().apply {
    @Suppress("DEPRECATION")
    this.locale = locale
    When calling this.locales doReturn LocaleList(locale)
}

fun configureResourceGetText(
    resources: Resources,
    id: Int,
    nameId: String,
    text: CharSequence
) {
    When calling resources.getResourceEntryName(id) doReturn nameId
    When calling resources.getText(id) doReturn text
}

fun configureResourceQuantityString(resources: Resources, quantity: Int, quantityString: String) {
    When calling resources.getQuantityString(
        R.plurals.com_jcminarro_philology_quantity_string,
        quantity
    ) doReturn quantityString
}

fun configureResourceGetIdException(resources: Resources, id: Int) {
    When calling resources.getResourceEntryName(id) doThrow Resources.NotFoundException()
}

fun configureResourceGetQuantityText(
    resources: Resources, id: Int, nameId: String, quantity: Int, text: CharSequence
) {
    When calling resources.getResourceEntryName(id) doReturn nameId
    When calling resources.getQuantityText(id, quantity) doReturn text
}

fun configureResourceGetTextArray(
    resources: Resources, id: Int, nameId: String, textArray: Array<CharSequence>
) {
    When calling resources.getResourceEntryName(id) doReturn nameId
    When calling resources.getTextArray(id) doReturn textArray
}

fun clearPhilology() {
    Philology.init(object : PhilologyRepositoryFactory {
        override fun getPhilologyRepository(locale: Locale): PhilologyRepository? = null
    })
}

fun createRepository(
    nameId: String,
    quantity: String? = null,
    text: CharSequence? = null,
    textArray: Array<CharSequence>? = null
): PhilologyRepository =
    object : PhilologyRepository {
        override fun getText(key: String) = text.takeIf { key == nameId }

        override fun getPlural(key: String, quantityString: String): CharSequence? {
            return text.takeIf { key == nameId && quantity == quantity }
        }

        override fun getTextArray(key: String) = textArray.takeIf { key == nameId }
    }

fun createFactory(vararg repositoryPairs: Pair<Locale, PhilologyRepository>): PhilologyRepositoryFactory =
    object : PhilologyRepositoryFactory {
        override fun getPhilologyRepository(locale: Locale): PhilologyRepository? =
            repositoryPairs.firstOrNull { it.first == locale }?.second
    }

fun configurePhilology(repository: PhilologyRepository, locale: Locale = Locale.ENGLISH) {
    Philology.init(createFactory(locale to repository))
}

fun View.createAttributeSet(vararg attributeType: ResourceIdAttribute): AttributeSet {
    val context = mock<Context>()
    When calling this.context doReturn context
    return mock<AttributeSet>().apply {
        attributeType.forEachIndexed { _, at ->
            val typedArray = mock<TypedArray>()
            When calling typedArray.getResourceId(0, -1) doReturn at.stringResId
            When calling context.obtainStyledAttributes(
                this,
                intArrayOf(at.attrResId)
            ) doReturn typedArray
        }
    }
}

sealed class ResourceIdAttribute(@AttrRes val attrResId: Int, open val stringResId: Int) {
    data class TextAttribute(override val stringResId: Int) :
        ResourceIdAttribute(android.R.attr.text, stringResId)

    data class HintAttribute(override val stringResId: Int) :
        ResourceIdAttribute(android.R.attr.hint, stringResId)

    data class TitleAttribute(override val stringResId: Int) :
        ResourceIdAttribute(android.R.attr.title, stringResId)

    data class CompatTitleAttribute(override val stringResId: Int) :
        ResourceIdAttribute(androidx.appcompat.R.attr.title, stringResId)

    data class SubtitleAttribute(override val stringResId: Int) :
        ResourceIdAttribute(android.R.attr.subtitle, stringResId)

    data class CompatSubtitleAttribute(override val stringResId: Int) :
        ResourceIdAttribute(androidx.appcompat.R.attr.subtitle, stringResId)
}
