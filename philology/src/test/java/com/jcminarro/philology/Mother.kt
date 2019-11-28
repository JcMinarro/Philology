package com.jcminarro.philology

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.os.LocaleList
import android.util.AttributeSet
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
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

fun createAttributeSet(
    vararg attributeType: AttributeType,
    context: Context = mock()
): AttributeSet = mock<AttributeSet>().apply {
    attributeType.forEachIndexed { index, at ->
        when (at) {
            is HardcodedAttribute -> {
                whenever(this.getAttributeResourceValue(eq(index), any()))
                    .doAnswer { it.arguments[1] as Int }
            }
            is ResourceIdAttribute -> {
                When calling this.getAttributeResourceValue(eq(index), any()) doReturn index
            }
            is StyleAttribute -> {
                val styleAttributes = createStyledAttributes(at.styleAttributes)
                When calling context.obtainStyledAttributes(
                    eq(index),
                    any()
                ) doReturn styleAttributes
                When calling this.getAttributeResourceValue(eq(index), any()) doReturn index
            }
        }
        When calling this.getAttributeName(index) doReturn at.key
    }
    When calling this.attributeCount doReturn attributeType.size
}

fun createStyledAttributes(styleAttributes: IntArray): TypedArray = mock<TypedArray>().apply {
    styleAttributes.forEachIndexed { index, _ ->
        When calling this.getResourceId(eq(index), any()) doReturn index
    }
}

sealed class AttributeType {
    abstract val key: String
}

data class HardcodedAttribute(override val key: String) : AttributeType()
data class ResourceIdAttribute(override val key: String) : AttributeType()
data class StyleAttribute(
    val styleAttributes: IntArray,
    override val key: String = "style"
) : AttributeType()
