package com.jcminarro.philology

import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import java.util.Locale

fun createConfiguration(locale: Locale = Locale.ENGLISH): Configuration = mock<Configuration>().apply{
    this.locale = locale
    When calling this.locales doReturn LocaleList(locale)
}

fun configureResourceGetText(resources: Resources,
                             id: Int,
                             nameId: String,
                             text: CharSequence) {
    When calling resources.getResourceEntryName(id) doReturn nameId
    When calling resources.getText(id) doReturn text
}

fun configureResourceGetTextException(resources: Resources, id: Int) {
    When calling resources.getResourceEntryName(id) doThrow Resources.NotFoundException()
}

fun clearPhilology() {
    Philology.init(object : PhilologyRepositoryFactory{
        override fun getPhilologyRepository(locale: Locale): PhilologyRepository? = null
    })
}

fun createRepository(nameId: String, text: CharSequence): PhilologyRepository =
        object : PhilologyRepository {
            override fun getText(key: String): CharSequence? = text.takeIf {key == nameId}
        }

fun createFactory(vararg repositoryPairs: Pair<Locale, PhilologyRepository>): PhilologyRepositoryFactory =
        object : PhilologyRepositoryFactory {
            override fun getPhilologyRepository(locale: Locale): PhilologyRepository? =
                repositoryPairs.firstOrNull {it.first == locale}?.second
        }

fun configurePhilology(repository: PhilologyRepository, locale: Locale = Locale.ENGLISH) {
    Philology.init(createFactory(locale to repository))
}