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
    override fun getPhilologyRepository(locale: Locale): PhilologyRepository? = when (locale) {
        Locale.ENGLISH -> EnglishPhilologyRepository
        Locale("es", "ES") -> SpanishPhilologyRepository
        Locale("ru", "RU") -> RussianPhilologyRepository
// If we don't support a language we could return null as PhilologyRepository and
// values from the strings resources file will be used
        else -> null
    }
}

object EnglishPhilologyRepository : PhilologyRepository {
    private val pluralsSample = mapOf("one" to "test one", "other" to "test other")
    private val pluralsSampleFormat = mapOf("one" to "%s test one", "other" to "%s tests")
    private val resourceSample = mapOf(
        "plurals_sample" to pluralsSample,
        "plurals_sample_format" to pluralsSampleFormat
    )

    override fun getText(key: String): CharSequence? = when (key) {
        "label" -> "New value for the `label` key, it could be fetched from a database or an external API server"
        else -> null
    }

    override fun getPlural(key: String, quantityString: String): CharSequence? {
        return resourceSample[key]?.get(quantityString)
    }
}

object SpanishPhilologyRepository : PhilologyRepository {
    private val pluralsSample = mapOf("one" to "prueba uno", "other" to "prueba otro")
    private val pluralsSampleFormat = mapOf("one" to "prueba uno %s", "other" to "prueba otro %s")
    private val resourceSample = mapOf(
        "plurals_sample" to pluralsSample,
        "plurals_sample_format" to pluralsSampleFormat
    )

    override fun getText(key: String): CharSequence? = when (key) {
        "label" -> "Nuevo valor para la clave `label`, puede ser obtenida de una base de datos o un servidor externo"
        "toolbar_title" -> "Philology muestra"
        "plural_quantity_hint" -> "Cantidad plural editar"
        "plural_quantity_format_hint" -> "Formato de edición de cantidad plural"
        else -> null
    }

    override fun getPlural(key: String, quantityString: String): CharSequence? {
        return resourceSample[key]?.get(quantityString)
    }

    override fun getTextArray(key: String) = when (key) {
        "days" -> arrayOf<CharSequence>(
            "lunes",
            "martes",
            "miércoles",
            "jueves",
            "viernes",
            "sábado",
            "domingo"
        )
        else -> null
    }
}

object RussianPhilologyRepository : PhilologyRepository {
    private val pluralsSample = mapOf(
        "one" to "один тест",
        "other" to "другие тесты",
        "few" to "несколько тестов",
        "many" to "много тестов"
    )
    private val pluralsSampleFormat = mapOf(
        "one" to "%s тест",
        "other" to "%s тесты",
        "few" to "%s теста",
        "many" to "%s тестов"
    )
    private val resourceSample = mapOf(
        "plurals_sample" to pluralsSample,
        "plurals_sample_format" to pluralsSampleFormat
    )

    override fun getText(key: String): CharSequence? = when (key) {
        "label" -> "Новое значение для ключа `label`, его можно получить из базы данных или внешнего сервера API"
        "toolbar_title" -> "Philology Пример"
        "plural_quantity_hint" -> "Редактировать множественное количество"
        "plural_quantity_format_hint" -> "Формат редактирования множественного числа"
        else -> null
    }

    override fun getPlural(key: String, quantityString: String): CharSequence? {
        return resourceSample[key]?.get(quantityString)
    }

    override fun getTextArray(key: String) = when (key) {
        "days" -> arrayOf<CharSequence>(
            "понедельник",
            "вторник",
            "среда",
            "четверг",
            "пятница",
            "суббота",
            "воскресенье"
        )
        else -> null
    }
}
