package com.jcminarro.philology.sample

import android.app.Application
import com.jcminarro.philology.Philology
import com.jcminarro.philology.PhilologyInterceptor
import com.jcminarro.philology.PhilologyRepository
import com.jcminarro.philology.PhilologyRepositoryFactory
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
    override fun getPhilologyRepository(locale: Locale): PhilologyRepository? = when{
        Locale.ENGLISH.language  == locale.language -> EnglishPhilologyRepository
        Locale("es", "ES").language == locale.language -> SpanishPhilologyRepository
// If we don't support a language we could return null as PhilologyRepository and
// values from the strings resources file will be used
        else -> null
    }
}

object EnglishPhilologyRepository : PhilologyRepository {
    override fun getText(key: String): CharSequence? = when (key) {
        "label" -> "New value for the `label` key, it could be fetched from a database or an external API server"
// If we don't want reword an strings we could return null and the value from the string resources file will be used
        else -> null
    }
}

object SpanishPhilologyRepository : PhilologyRepository {
    override fun getText(key: String): CharSequence? = when (key) {
        "label" -> "Nuevo valor para la clave `label`, puede ser obtenida de una base de datos o un servidor externo"
        else -> null
    }

}