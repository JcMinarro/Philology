package com.jcminarro.philology.sample

import android.app.Application
import com.jcminarro.philology.Philology
import com.jcminarro.philology.PhilologyInterceptor
import com.jcminarro.philology.PhilologyRepository
import com.jcminarro.philology.PhilologyRepositoryFactory
import com.jcminarro.philology.Resource
import com.jcminarro.philology.Resource.Plural
import com.jcminarro.philology.Resource.Text
import io.github.inflationx.viewpump.ViewPump
import java.util.Locale

class App : Application() {

    override fun onCreate() {
        super.onCreate()
// Init Philology with our PhilologyRepositoryFactory
        Philology.init(MyPhilologyRepositoryFactory)
// Add PhilologyInterceptor to ViewPump
// If you are already using Calligraphy you can add both interceptors, there is no problem
        ViewPump.init(ViewPump.builder().addInterceptor(PhilologyInterceptor).build())
    }
}

object MyPhilologyRepositoryFactory : PhilologyRepositoryFactory {
    override fun getPhilologyRepository(locale: Locale): PhilologyRepository? = when {
        Locale.ENGLISH.language == locale.language -> EnglishPhilologyRepository
        Locale("es", "ES").language == locale.language -> SpanishPhilologyRepository
// If we don't support a language we could return null as PhilologyRepository and
// values from the strings resources file will be used
        else -> null
    }
}

object EnglishPhilologyRepository : PhilologyRepository {
    private val pluralsSample = mapOf("one" to "test one", "other" to "test other")
    private val pluralsSampleFormat = mapOf("one" to "test one %s", "other" to "test other %s")
    private val resourceSample = mapOf(
        "plurals_sample" to pluralsSample,
        "plurals_sample_format" to pluralsSampleFormat
    )

    override fun getText(resource: Resource): CharSequence? = when (resource) {
        is Text -> when (resource.key) {
            "label" -> "New value for the `label` key, it could be fetched from a database or an external API server"
            else -> null
        }
        is Plural -> resourceSample[resource.key]?.get(resource.quantityKeyword)
// If we don't want reword an strings we could return null and the value from the string resources file will be used
        else -> null
    }
}

object SpanishPhilologyRepository : PhilologyRepository {
    private val pluralsSample = mapOf("one" to "prueba uno", "other" to "prueba otro")
    private val pluralsSampleFormat = mapOf("one" to "prueba uno %s", "other" to "prueba otro %s")
    private val resourceSample = mapOf(
        "plurals_sample" to pluralsSample,
        "plurals_sample_format" to pluralsSampleFormat
    )

    override fun getText(resource: Resource): CharSequence? = when (resource) {
        is Text -> when (resource.key) {
            "label" -> "Nuevo valor para la clave `label`, puede ser obtenida de una base de datos o un servidor externo"
            else -> null
        }
        is Plural -> resourceSample[resource.key]?.get(resource.quantityKeyword)
// If we don't want reword an strings we could return null and the value from the string resources file will be used
        else -> null
    }
}