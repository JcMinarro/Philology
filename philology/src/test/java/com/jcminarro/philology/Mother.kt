package com.jcminarro.philology

import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import android.util.AttributeSet
import com.ibm.icu.impl.number.DecimalQuantity
import com.jcminarro.philology.Resource.Plural
import com.jcminarro.philology.Resource.Text
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.eq
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.mockito.Mockito
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

fun configureResourceGetIdException(resources: Resources, id: Int) {
    When calling resources.getResourceEntryName(id) doThrow Resources.NotFoundException()
}

fun configureResourceGetQuantityText(
    resources: Resources, id: Int, nameId: String, quantity: Int, text: CharSequence
) {
    When calling resources.getResourceEntryName(id) doReturn nameId
    When calling resources.getQuantityText(id, quantity) doReturn text
}

fun clearPhilology() {
    Philology.init(object : PhilologyRepositoryFactory {
        override fun getPhilologyRepository(locale: Locale): PhilologyRepository? = null
    })
}

fun createRepository(nameId: String, quantity: String?, text: CharSequence): PhilologyRepository =
    object : PhilologyRepository {
        override fun getText(resource: Resource): CharSequence? = when (resource) {
            is Text -> text.takeIf { resource.key == nameId }
            is Plural -> text.takeIf {
                resource.key == nameId && resource.quantityKeyword == quantity
            }
        }
    }

fun createFactory(vararg repositoryPairs: Pair<Locale, PhilologyRepository>): PhilologyRepositoryFactory =
    object : PhilologyRepositoryFactory {
        override fun getPhilologyRepository(locale: Locale): PhilologyRepository? =
            repositoryPairs.firstOrNull { it.first == locale }?.second
    }

fun configurePhilology(repository: PhilologyRepository, locale: Locale = Locale.ENGLISH) {
    Philology.init(createFactory(locale to repository))
}

fun createAttributeSet(vararg attributeType: AttributeType): AttributeSet = mock<AttributeSet>().apply {
    attributeType.forEachIndexed { index, at ->
        when (at) {
            is HardcodedAttribute -> {
                Mockito.`when`(this.getAttributeResourceValue(eq(index), org.amshove.kluent.any()))
                    .doAnswer { it.arguments[1] as Int }
            }
            is ResourceIdAttribute -> {
                When calling this.getAttributeResourceValue(
                    eq(index),
                    org.amshove.kluent.any()
                ) doReturn index
            }
        }
        When calling this.getAttributeName(index) doReturn at.key
    }
    When calling this.attributeCount doReturn attributeType.size
}

sealed class AttributeType {
    abstract val key: String
}

data class HardcodedAttribute(override val key: String) : AttributeType()
data class ResourceIdAttribute(override val key: String) : AttributeType()